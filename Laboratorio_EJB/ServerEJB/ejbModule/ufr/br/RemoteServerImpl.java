package ufr.br;

import javax.ejb.Stateless;

@Stateless
public class RemoteServerImpl implements RemoteServer{

	@Override
	public String getString(String nome) {
		System.out.println("Recebido: " + nome); //mostrado no servidor
		return "Hello " + " " + nome; // string vai para o cliente
	}

}
