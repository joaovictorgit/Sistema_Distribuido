
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPCliente {
    
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try{
            int porta = 4444;
            socket = new Socket("localhost", porta);
            
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("trabalhoSD");
            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String data = in.readUTF();
            System.out.println("Received: " + data);
            
        }catch(UnknownHostException e){
            System.out.println("Socket: " + e.getMessage());
        } catch(EOFException e){
            System.out.println("EOF: " + e.getMessage());
        } catch (IOException e){
            System.out.println("readLine: " + e.getMessage());
        } finally {
            if(socket != null){
                try {
                    socket.close();
                } catch(IOException e){
                    System.out.println("close: " + e.getMessage());
                }
            }
        }
        
        
    }
}
