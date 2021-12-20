package servidor;

import java.io.Serializable;

import interfaces.IChatClient;

public class Chat implements Serializable{

	public String name;
	public IChatClient client;
	
	public Chat(String name, IChatClient client){
		this.name = name;
		this.client = client;
	}

	public String getName(){
		return name;
	}
	public IChatClient getClient(){
		return client;
	}
	
	
}
