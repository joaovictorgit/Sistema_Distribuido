package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class TCPServidor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        
            ServerSocket ss = new ServerSocket(4444);
            System.out.println("ServerSocket aguardando conexão...");
            Socket socket = ss.accept();
            System.out.println("Conexão de " + socket + "!");
            
            InputStream input = socket.getInputStream();
            ObjectInputStream obj_in = new ObjectInputStream(input);
            
            List<Message> list =  (List<Message>) obj_in.readObject();
            System.out.println("Received [" + list.size() + "] messages from: " + socket);
            System.out.println("Mensagens: ");
            list.forEach((msg) -> System.out.println(msg.getMsg()));
            
            ss.close();
            socket.close();
        
        
        
        /*try{
            int porta = 4444;
            ServerSocket servidor = new ServerSocket(porta);
            System.out.println("Servidor na porta " + porta);
            while(true){
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                saida.flush();
                saida.writeObject(new Date());
                saida.close();
                cliente.close();
            }
        } catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
        */
    }
}
