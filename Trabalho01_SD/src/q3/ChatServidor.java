package q3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ChatServidor {
    
    public static final int porta = 4000;
    private ServerSocket server;
    private final List<ClientSocket> clients = new LinkedList<>();
    
    public void start() throws IOException{
        server = new ServerSocket(porta);
        System.out.println("Conexão estabelecida!");
        clientConectionLoop();
    }
    
    private void clientConectionLoop() throws IOException{
        while(true){
            ClientSocket clientSocket = new ClientSocket(server.accept());
            //System.out.println("Cliente " + clientSocket.getRemoteSocketAddress() + " conectou");
            //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clients.add(clientSocket);
            
            new Thread(() ->  clientMessageLoop(clientSocket)).start();
            
            //System.out.println("Messagem recebida do cliente " + clientSocket.getRemoteSocketAddress() + ": " + msg);
        }
    }
    
    private void clientMessageLoop(ClientSocket clientSocket){
        String msg;
        try{
            while((msg = clientSocket.getMessage()) != null){
                if("sair".equalsIgnoreCase((msg)))
                    return;
                System.out.printf("Msg recebida do cliente %s: %s\n",clientSocket.getRemoteSocketAddress(), msg);
                sendMsgAll(clientSocket, msg);
            }
        } finally{
            System.out.printf("Cliente %s saiu",clientSocket.getRemoteSocketAddress());
            clientSocket.close();
        }
    }
    
    private void sendMsgAll(ClientSocket sender, String msg){
        Iterator<ClientSocket> iterator = clients.iterator();
        while(iterator.hasNext()){
            ClientSocket clientSocket = iterator.next();
            if(!sender.equals(clientSocket)){
                if(!clientSocket.sendMsg("Cliente " + sender.getRemoteSocketAddress() + ":" + msg)){ 
                    iterator.remove();
                }
            }
        }
    }
    
    public static void main(String [] args){
        
        try {
            ChatServidor chat = new ChatServidor();
            chat.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o servidor: " + ex.getMessage());   
        }
        System.out.println("Sistema finalizado!");
    }
}
