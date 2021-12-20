package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;

public interface IChatServer extends Remote {
		
	public void updateChat(String userName, String chatMessage)throws RemoteException;
	
	public void identity(RemoteRef ref)throws RemoteException;
	
	public void register(String[] details)throws RemoteException;
	
	public void exit(String userName)throws RemoteException;
	
	public void sendPM(int[] privateGroup, String privateMessage)throws RemoteException;
}


