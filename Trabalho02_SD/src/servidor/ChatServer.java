package servidor;

import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;

import interfaces.IChatClient;
import interfaces.IChatServer;

public class ChatServer extends UnicastRemoteObject implements IChatServer {
	String line = "---------------------------------------------\n";
	private Vector<Chat> chatters;
	private static final long serialVersionUID = 1L;
	

	public ChatServer() throws RemoteException {
		super();
		chatters = new Vector<Chat>(10, 1);
	}
	
	
	public static void main(String[] args) {
		startRMIRegistry();	
		String hostName = "localhost";
		String serviceName = "GroupChatSD";
		
		if(args.length == 2){
			hostName = args[0];
			serviceName = args[1];
		}
		
		try{
			IChatServer hello = new ChatServer();
			Naming.rebind("rmi://" + hostName + "/" + serviceName, hello);
			System.out.println("Servidor rodando...");
		}
		catch(Exception e){
			System.out.println("Problema ao iniciar servidor");
		}	
	}

	public static void startRMIRegistry() {
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("Servidor ok");
		}
		catch(RemoteException e) {
			e.printStackTrace();
		}
	}
		
	
	public String welcome(String ClientName) throws RemoteException {
		System.out.println(ClientName + " enviou uma message");
		return "Olá " + ClientName + " chat servidor";
	}

	public void updateChat(String name, String nextPost) throws RemoteException {
		String message =  name + " : " + nextPost + "\n";
		sendToAll(message);
	}
	
	
	@Override
	public void identity(RemoteRef ref) throws RemoteException {	
		
		try{
			System.out.println(line + ref.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void register(String[] details) throws RemoteException {	
		System.out.println(new Date(System.currentTimeMillis()));
		System.out.println(details[0] + " juntou-se ao servidor");
		System.out.println(details[0] + " nome de usuário : " + details[1]);
		System.out.println(details[0] + " serviço RMI : " + details[2]);
		registerMessage(details);
	}

	
	private void registerMessage(String[] details){		
		try{
			IChatClient nextClient = ( IChatClient )Naming.lookup("rmi://" + details[1] + "/" + details[2]);
			
			chatters.addElement(new Chat(details[0], nextClient));
			
			nextClient.sendMessage("[Servidor] : Olá " + details[0] + " pode iniciar a conversa\n");
			
			sendToAll("[Servidor] : " + details[0] + " juntou-se ao grupo.\n");
			
			updateUserList();		
		}
		catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
		}
	}
	
	
	private void updateUserList() {
		String[] currentUsers = getUserList();	
		for(Chat c : chatters){
			try {
				c.getClient().updateList(currentUsers);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	

	
	private String[] getUserList(){
		String[] allUsers = new String[chatters.size()];
		for(int i = 0; i< allUsers.length; i++){
			allUsers[i] = chatters.elementAt(i).getName();
		}
		return allUsers;
	}
	

	public void sendToAll(String newMessage){	
		for(Chat c : chatters){
			try {
				c.getClient().sendMessage(newMessage);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}

	
	@Override
	public void exit(String userName) throws RemoteException{
		
		for(Chat c : chatters){
			if(c.getName().equals(userName)){
				System.out.println(line + userName + " deixou a senssão");
				System.out.println(new Date(System.currentTimeMillis()));
				chatters.remove(c);
				break;
			}
		}		
		if(!chatters.isEmpty()){
			updateUserList();
		}			
	}
	

	
	@Override
	public void sendPM(int[] privateGroup, String privateMessage) throws RemoteException{
		Chat pc;
		for(int i : privateGroup){
			pc= chatters.elementAt(i);
			pc.getClient().sendMessage(privateMessage);
		}
	}
	
}



