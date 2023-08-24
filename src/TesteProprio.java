import models.Pessoa;
import models.Reuniao;

public class TesteProprio {
    public static void main(String[] args) throws Exception {
        Pessoa p1 = new Pessoa("Allan");
        Pessoa p2 = new Pessoa("Marcio");
        Pessoa p3 = new Pessoa("Claudio");
        Pessoa p4 = new Pessoa("Wagner");
        System.out.println(p1.toString());
        
        Reuniao r1 = new Reuniao("10/10/2023");
        r1.adicionarPessoa(p1);
        r1.adicionarPessoa(p2);
        r1.adicionarPessoa(p3);
        r1.adicionarPessoa(p4);
        System.out.println(r1.toString());
    }
}
