package com.arquiteturap2p;

import java.util.*;

/**
 * Gerenciador de threads - controla todas as threads ativas do peer
 * Usado para garantir encerramento limpo de todas as conexões
 */
public class PeerManager {
    // Lista thread-safe de todas as threads ativas
    private static List<Thread> allThreads = Collections.synchronizedList(new ArrayList<>());
    
    // Adiciona thread à lista de controle
    public static void addThread(Thread thread) {
        allThreads.add(thread);
    }
    
    // Encerra todas as threads ativas
    public static void shutdownAll() {
        synchronized (allThreads) {
            // Interrompe cada thread
            for (Thread thread : allThreads) {
                thread.interrupt();
            }
            // Limpa a lista
            allThreads.clear();
        }
    }
}