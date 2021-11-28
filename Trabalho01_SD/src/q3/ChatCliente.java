package q3;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatCliente implements Runnable{
    
    
    private static final String endereco_servidor = "127.0.0.1";
    private ClientSocket clientSocket;
    private Scanner input;
    
    
    public ChatCliente(){
        input = new Scanner(System.in);
    }
    
    public void start() throws IOException{
        try{
        
            clientSocket = new ClientSocket(new Socket(endereco_servidor, ChatServidor.porta));
            //this.out = new PrintWriter(client.getOutputStream(), true);
            //System.out.println("Cliente conectado ao servidor em " + endereco_servidor + ":" + ChatServidor.porta);
            System.out.println("Conexão estabelecida!");
            new Thread(this).start();
            messageLoop();
        } finally {
            clientSocket.close();
        }
    }
    
    @Override
    public void run(){
        String msg;
        
        while((msg = clientSocket.getMessage()) != null){
            System.out.printf("Msg reccebida: %s\n", msg);
        }
        
    }
    
    private void messageLoop() throws IOException{
        String msg;
        do{
            System.out.println("Digite uma mensagem (ou sair para finalizar): ");
            msg = input.nextLine();
            clientSocket.sendMsg(msg);
            
        }while(!msg.equalsIgnoreCase("sair"));
    }
    
    public static void main(String [] args){
        try {
            ChatCliente client = new ChatCliente();
            client.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o cliente: " + ex.getMessage() + " conector");
        }
        System.out.println("Cliente finalizado!");
    }
}
