package input;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import person.Pessoa;


public class PessoasInputStreamTeste {
    
    public static void main(String[] args) throws IOException {
        int tamanho = 10;
        Pessoa[] pessoa = new Pessoa[tamanho];
        DataInputStream dip = new DataInputStream(new InputStream(){
            @Override
            public int read() throws IOException {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

        });
        
        PessoasInputStream pos = new PessoasInputStream(pessoa, dip);
        System.out.println(pos);
        pos.lerSystem_in();
        pos.lerArquivo();
        pos.enviarTCP();
        //pos.imprimir();
    }
    
    
    
}
