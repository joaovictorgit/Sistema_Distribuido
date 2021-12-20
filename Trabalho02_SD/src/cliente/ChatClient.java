package cliente;
import java.net.MalformedURLException;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import interfaces.IChatClient;
import interfaces.IChatServer;


public class ChatClient  extends UnicastRemoteObject implements IChatClient {
	
	private static final long serialVersionUID = 1L;
	ClientRMI_Frame chat;
	String serviceName = "GroupChatSD";
	String clientName;
	String name;
	IChatServer server;
	boolean connectionProblem = false;

	
	
	public ChatClient(ClientRMI_Frame aChat, String userName) throws RemoteException {
		super();
		this.chat = aChat;
		this.name = userName;
		this.clientName = "ClientListenService_" + userName;
	}

	
	
	public void start() throws RemoteException {		
		String[] details = {name, "localhost", clientName};	

		try {
			Naming.rebind("rmi://localhost/" + clientName, this);
			server = ( IChatServer )Naming.lookup("rmi://localhost/" + serviceName);	
		} 
		catch (ConnectException  e) {
			System.out.println("O servidor parece estar indisponível \n Por favor, tente mais tarde"+"Connection problem");
			connectionProblem = true;
			e.printStackTrace();
		}
		catch(NotBoundException | MalformedURLException me){
			connectionProblem = true;
			me.printStackTrace();
		}
		if(!connectionProblem){
			registerServer(details);
		}	
		System.out.println("Servidor em execução\n");
	}


	
	public void registerServer(String[] details) {		
		try{
			server.identity(this.ref);
			server.register(details);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
	@Override
	public void sendMessage(String msg) throws RemoteException {
		System.out.println(msg);
		chat.textArea.append(msg);
		chat.textArea.setCaretPosition(chat.textArea.getDocument().getLength());
	}

	
	@Override
	public void updateList(String[] users) throws RemoteException {

		if(users.length < 2){
			chat.msgButton.setEnabled(false);
		}
		chat.userPanel.remove(chat.clientPanel);
		chat.setClient(users);
		chat.clientPanel.repaint();
		chat.clientPanel.revalidate();
	}

}













