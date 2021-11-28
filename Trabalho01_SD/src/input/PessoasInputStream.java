/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import person.Pessoa;
import server.Message;

public class PessoasInputStream extends InputStream {
    
    private InputStream in;
    Pessoa[] pessoas;
    private String nome;
    private long cpf;
    private int idade;
    
    public PessoasInputStream(Pessoa[] p, InputStream i){
        this.in = i;
        this.pessoas = p;
    }
    
    public void lerSystem_in(){
        Scanner input = new Scanner(System.in);
        int qtd_pessoas = this.pessoas.length;
        try{
            Pessoa pessoa;
            for(int i = 0; i < qtd_pessoas; i++){
                System.out.print("Nome: ");
                this.nome = input.next();
                System.out.print("CPF: ");
                this.cpf = input.nextLong();
                System.out.print("Idade: ");
                this.idade = input.nextInt();
                
                Pessoa p = new Pessoa(nome, cpf, idade);
                this.pessoas[i] = p;
            }
        }catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
        
    }
    
    public void lerArquivo(){
        String filename = "C:\\Users\\joaov\\Dropbox\\PC\\Documents\\NetBeansProjects\\Trabalho01_SD\\src\\files\\arquivo.txt";
        try{
            FileInputStream file = new FileInputStream(filename);
            int i = 0;
            while((i = file.read())!= -1){
                System.out.print((char)i);
            }
            file.close();
        }catch(Exception e){
            System.out.println("Erro " + e.getMessage());
        }
    }
    
    public void enviarTCP() throws IOException{
        int qtd_pessoas = pessoas.length;
        
        Socket socket = new Socket("localhost", 4444);
        System.out.println("Conectado para enviar dados via TCP!");
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream obj_out = new ObjectOutputStream(out);
        List<Message> msg = new ArrayList<>();
            for(Pessoa pessoa : pessoas){
                try{
                    int tamanho_bytes = pessoa.getNome().getBytes().length;
                    String nome = pessoa.getNome();
                    long cpf = pessoa.getCpf();
                    int idade = pessoa.getIdade();
                    String res = "Tamanho: " + tamanho_bytes + " Nome: " + nome + " Cpf: " + cpf + " Idade: " + idade+"\n"; 
                    
                    msg.add(new Message(res));
                }catch(Exception e){}
            }
        System.out.println("Enviando dados para o Servidor");
        obj_out.writeObject(msg);
        System.out.println("Fechando socket");
        socket.close();
    }
    
    public void imprimir(){
        for(Pessoa pessoa : pessoas){
            System.out.println(pessoa.getNome() + " " + pessoa.getCpf() + " " + pessoa.getIdade());
        }
    }

    @Override
    public String toString() {
        return "PessoasOutputStream[getClass()="+ getClass() + ", HashCode() = " + hashCode() 
                    + ", toString()=" + super.toString() +
                ']';
    }

    @Override
    public int read() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
