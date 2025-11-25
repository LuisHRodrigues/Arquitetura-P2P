package com.arquiteturap2p;

import java.io.*;
import java.net.*;

/**
 * Thread para comunicação com um peer específico
 * Gerencia conexão de saída para outro peer
 */
public class PeerThread extends Thread {
    private Socket socket;   // Conexão com o peer
    private String username; // Nome do usuário local
    private PrintWriter out; // Stream para enviar mensagens

    // Construtor - recebe socket conectado e nome do usuário
    public PeerThread(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    // Loop principal da thread - escuta mensagens do peer
    @Override
    public void run() {
        try {
            // Configura streams de entrada e saída
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String message;
            // Lê mensagens continuamente
            while ((message = in.readLine()) != null) {
                System.out.println("Recebido: " + message);
            }
        } catch (IOException e) {
            // Conexão foi encerrada
            System.out.println("Conexão encerrada: " + e.getMessage());
        } finally {
            // Sempre fecha o socket ao terminar
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Envia mensagem para o peer conectado
    public void sendMessage(String message) {
        if (out != null) {
            out.println(username + ": " + message);
        }
    }
}