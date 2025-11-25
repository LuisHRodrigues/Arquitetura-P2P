# Sistema P2P (Peer-to-Peer) em Java

Sistema de comunicação peer-to-peer simples implementado em Java, onde cada peer pode se conectar a outros peers e trocar mensagens em tempo real.

## 📋 Funcionalidades

- **Comunicação P2P**: Peers se conectam diretamente entre si
- **Broadcast de mensagens**: Mensagens são enviadas para todos os peers conectados
- **Conexão dinâmica**: Peers podem se conectar a múltiplos outros peers
- **Saída limpa**: Peers podem sair da rede sem deixar processos órfãos

## 🏗️ Arquitetura

O sistema é composto por 4 classes principais:

- **`Peer.java`**: Classe principal que gerencia entrada do usuário e coordena as conexões
- **`ServerThread.java`**: Thread servidor que aceita conexões de outros peers
- **`PeerThread.java`**: Thread para comunicação com um peer específico
- **`PeerManager.java`**: Gerenciador de threads para controle de shutdown

## 🚀 Como Compilar e Executar

### Pré-requisitos

1. Clone ou baixe o projeto
2. Navegue até a pasta do projeto no terminal

### 1. Compilação

```bash
# Navegue até a pasta do código fonte
cd p2p/src/main/java

# Compile todas as classes
javac com/arquiteturap2p/*.java
```

### 2. Execução

Abra **3 terminais diferentes** e execute em cada um:

**Terminal 1 (Ronaldo):**

```bash
cd p2p/src/main/java
java com.arquiteturap2p.Peer 4441 Ronaldo
```

**Terminal 2 (Pelé):**

```bash
cd p2p/src/main/java
java com.arquiteturap2p.Peer 4442 Pelé
```

**Terminal 3 (Maradona):**

```bash
cd p2p/src/main/java
java com.arquiteturap2p.Peer 4443 Maradona
```

### 3. Conectando os Peers

Após iniciar os peers, conecte-os digitando nos terminais:

- **Ronaldo**: `c localhost 4442 localhost 4443`
- **Pelé**: `c localhost 4441 localhost 4443`
- **Maradona**: `c localhost 4441 localhost 4442`

## 📖 Comandos Disponíveis

| Comando            | Descrição                 | Exemplo            |
| ------------------ | ------------------------- | ------------------ |
| `c <host> <porta>` | Conecta a um peer         | `c localhost 4442` |
| `<mensagem>`       | Envia mensagem para todos | `Olá pessoal!`     |
| `e`                | Sair da rede              | `e`                |

## 🧪 Exemplo de Teste

1. **Inicie os 3 peers** conforme instruções acima
2. **Conecte todos entre si** usando comando `c`
3. **Digite mensagens** em qualquer terminal
4. **Veja as mensagens** aparecerem nos outros terminais
5. **Digite `e`** em um terminal para testar saída
6. **Confirme** que o peer parou de receber mensagens

## 📁 Estrutura do Projeto

```
p2p/
├── src/main/java/com/arquiteturap2p/
│   ├── Peer.java          # Classe principal
│   ├── ServerThread.java  # Servidor do peer
│   ├── PeerThread.java    # Comunicação com peers
│   └── PeerManager.java   # Gerenciador de threads
└── README.md              # Este arquivo
```

## ⚙️ Requisitos

- **Java 8+**
- **Sistema Operacional**: Windows, Linux ou macOS
- **Múltiplos terminais** para testar

### Comandos por Sistema Operacional

**Windows:**

```cmd
cd p2p\src\main\java
javac com\arquiteturap2p\*.java
java com.arquiteturap2p.Peer 4441 Ronaldo
```

**Linux/macOS:**

```bash
cd p2p/src/main/java
javac com/arquiteturap2p/*.java
java com.arquiteturap2p.Peer 4441 Ronaldo
```

## 🔧 Scripts Auxiliares

- **`teste.bat`**: Compila o projeto e mostra instruções
- **`teste_saida.bat`**: Teste específico para verificar saída de peers

## 📝 Observações

- Cada peer precisa de uma **porta única**
- Use **localhost** para testes locais
- O sistema funciona em **rede local** alterando o host
- Mensagens são **broadcast** para todos os peers conectados
