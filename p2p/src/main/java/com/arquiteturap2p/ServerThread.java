package com.arquiteturap2p;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Thread do servidor - aceita conexões de outros peers
 * Gerencia todas as conexões ativas e faz broadcast de mensagens
 */
public class ServerThread extends Thread {
    private ServerSocket serverSocket; // Socket servidor para aceitar conexões
    private Set<PrintWriter> writers = Collections.synchronizedSet(new HashSet<>()); // Lista de peers conectados
    private volatile boolean running = true; // Flag para controlar execução

    // Construtor - cria servidor na porta especificada
    public ServerThread(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    // Loop principal do servidor
    public void run() {
        try {
            while (running) {
                // Aceita nova conexão de peer
                Socket socket = serverSocket.accept();
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                writers.add(pw); // Adiciona à lista de peers conectados

                // Cria thread para escutar mensagens deste peer
                new Thread(() -> {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        String line;
                        // Lê mensagens enquanto conexão estiver ativa
                        while (running && (line = br.readLine()) != null) {
                            // Só exibe mensagem se peer ainda estiver ativo
                            if (Peer.isRunning()) {
                                System.out.println(line);
                            }
                        }
                    } catch (IOException e) {
                        // Conexão encerrada normalmente
                    } finally {
                        // Remove peer da lista quando desconecta
                        writers.remove(pw);
                    }
                }).start();
            }
        } catch (IOException e) {
            // Só mostra erro se servidor ainda estiver rodando
            if (running) {
                e.printStackTrace();
            }
        }
    }

    // Envia mensagem para todos os peers conectados
    public void broadcast(String user, String msg) {
        synchronized (writers) {
            Iterator<PrintWriter> it = writers.iterator();
            while (it.hasNext()) {
                PrintWriter pw = it.next();
                try {
                    // Envia mensagem formatada
                    pw.println(user + ": " + msg);
                    // Remove peer se conexão estiver com erro
                    if (pw.checkError()) {
                        it.remove();
                    }
                } catch (Exception e) {
                    // Remove peer com erro
                    it.remove();
                }
            }
        }
    }

    // Para o servidor e fecha todas as conexões
    public void shutdown() {
        running = false;
        try {
            // Fecha todas as conexões ativas
            synchronized (writers) {
                for (PrintWriter pw : writers) {
                    pw.close();
                }
                writers.clear();
            }
            // Fecha socket do servidor
            serverSocket.close();
        } catch (IOException e) {
            // Ignora erros no shutdown
        }
    }
}

