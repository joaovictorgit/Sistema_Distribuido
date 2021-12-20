package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IChatClient extends Remote{

	public void sendMessage(String msg) throws RemoteException;

	public void updateList(String[] users) throws RemoteException;
	
}
