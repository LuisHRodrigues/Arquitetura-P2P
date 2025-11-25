package com.arquiteturap2p;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Classe principal do Peer - representa um nó na rede P2P
 * Cada peer pode receber e enviar mensagens para outros peers
 */
public class Peer {
    // Flag global para controlar se o peer está ativo
    private static volatile boolean running = true;
    
    public static void main(String[] args) throws Exception {
        // Valida argumentos de entrada (porta e nome do usuário)
        if (args.length < 2) {
            System.out.println("Uso: java Peer <porta> <username>");
            return;
        }

        // Extrai porta e nome do usuário dos argumentos
        int port = Integer.parseInt(args[0]);
        String username = args[1];

        // Cria e inicia servidor para aceitar conexões de outros peers
        ServerThread server = new ServerThread(port);
        server.start();

        // Configura entrada do usuário
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Digite mensagens, 'c' para conectar a peers, 'e' para sair:");

        // Loop principal - processa comandos do usuário
        while (running) {
            String msg = br.readLine();
            
            // Comando para sair da rede
            if (msg == null || msg.equals("e")) {
                System.out.println("*** PEER SAINDO DA REDE ***");
                running = false;
                server.shutdown(); // Para o servidor
                System.out.println("*** CONEXOES FECHADAS ***");
                break;
            } 
            // Comando para conectar a outros peers (formato: c host1 porta1 host2 porta2)
            else if (msg.startsWith("c")) {
                String[] parts = msg.split(" ");
                // Processa pares de host/porta
                for (int i = 1; i < parts.length; i += 2) {
                    String host = parts[i];
                    int peerPort = Integer.parseInt(parts[i + 1]);
                    // Conecta ao peer e cria thread para comunicação
                    Socket socket = new Socket(host, peerPort);
                    PeerThread pt = new PeerThread(socket, username);
                    pt.start();
                }
            } 
            // Mensagem normal - envia para todos os peers conectados
            else {
                server.broadcast(username, msg);
            }
        }
        
        // Aguarda finalização e encerra processo
        Thread.sleep(500);
        System.exit(0);
    }
    
    // Retorna se o peer ainda está ativo
    public static boolean isRunning() {
        return running;
    }
}

