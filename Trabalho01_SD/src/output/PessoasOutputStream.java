package output;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import person.Pessoa;
import server.Message;

public class PessoasOutputStream extends OutputStream{
    private OutputStream op;
    private Pessoa[] pessoas;
    
    public PessoasOutputStream(){}
    
    public PessoasOutputStream(Pessoa[] p, OutputStream o){
        this.pessoas = p;
        this.op = o;
    }
    
    public void enviar(){
        int qtd_pessoas = pessoas.length;
        //System.out.println("Quantidade de pessoa: " + qtd_pessoas);
        for(Pessoa pessoa: pessoas){
            try{
                int tamanho_bytes = pessoa.getNome().getBytes().length;
                String nome = pessoa.getNome();
                long cpf = pessoa.getCpf();
                int idade = pessoa.getIdade();

                PessoasOutputStream pos = new PessoasOutputStream();
                //pos.enviar();

                System.out.println("Tamanho: " + tamanho_bytes + " Nome: "
                        + nome + " Cpf: " + cpf + " Idade: " + idade);
            }catch(Exception e){
                 //e.printStackTrace();
            }
            
        }
    }
    
    public void escreverArquivo() throws FileNotFoundException{
        int qtd_pessoas = pessoas.length;
        for(Pessoa pessoa: pessoas){
            
            String filename = "C:\\Users\\joaov\\Dropbox\\PC\\Documents\\NetBeansProjects\\Trabalho01_SD\\src\\files\\arquivo.txt";
            
            try {
                int tamanho_bytes = pessoa.getNome().getBytes().length;
                String nome = pessoa.getNome();
                long cpf = pessoa.getCpf();
                int idade = pessoa.getIdade();
                String res = "Tamanho: " + tamanho_bytes + " Nome: " + nome + " Cpf: " + cpf + " Idade: " + idade+"\n"; 
                
                FileOutputStream file = new FileOutputStream(filename, true);
                file.write(res.getBytes());
                System.out.print("Arquivado! " + res);
            }catch(Exception e){
                //e.printStackTrace();
            }
            
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
       
        /*
        
            try {
                for(Pessoa pessoa: pessoas){
                    int tamanho_bytes = pessoa.getNome().getBytes().length;
                    String nome = pessoa.getNome();
                    long cpf = pessoa.getCpf();
                    int idade = pessoa.getIdade();
                    String res = "Tamanho: " + tamanho_bytes + " Nome: " + nome + " Cpf: " + cpf + " Idade: " + idade+"\n"; 

                    Socket cliente = new Socket("localhost", 4444);
                    
                    if(cliente.isConnected()){
                        ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                        DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());
                        saida.writeChars(res);
                        Date data_atual = (Date)entrada.readObject();
                        System.out.println("Data recebida do servidor: " + data_atual.toString());
                        
                        entrada.close();
                        System.out.println("Conexão encerrada");
                    }
                    
                }
            }catch(Exception e){
                //System.out.println("Erro: " + e.getMessage());
            }
            */
       
    }
    
    @Override
    public void write(int b) throws IOException {
    }

    @Override
    public String toString() {
        return "PessoasOutputStream[getClass()="+ getClass() + ", HashCode() = " + hashCode() 
                    + ", toString()=" + super.toString() +
                ']';
    }
    
    
            
}
