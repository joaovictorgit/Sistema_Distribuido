package output;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import person.Pessoa;

public class PessoasOutputStreamTeste {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Pessoa[] pessoa = new Pessoa[10];
        
        Pessoa p1 = new Pessoa("Joao",123123123,20);
        Pessoa p2 = new Pessoa("Caio",312353,21);
        Pessoa p3 = new Pessoa("Maria",2312313,22);
        Pessoa p4 = new Pessoa("Ana",1645567,20);
        
        pessoa[0] = p1;
        pessoa[1] = p2;
        pessoa[2] = p3;
        pessoa[3] = p4;
        
        DataOutputStream dip = new DataOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                
            }
        });
        
        PessoasOutputStream pos = new PessoasOutputStream(pessoa, dip);
        System.out.println(pos);
        pos.enviar();
        pos.escreverArquivo();
        pos.enviarTCP();
    }
}
