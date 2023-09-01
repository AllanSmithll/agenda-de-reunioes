/*
 * Cadastrar class
 * @authors Allan Amancio e Marcio Jose
 */
package appsConsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;


import models.Pessoa;
import models.Reuniao;

public class Cadastrar {
	protected ObjectContainer manager;

	public Cadastrar() {
		try {
		   manager = Util.conectarBanco();
		   Pessoa p1 = new Pessoa("Allan");
		   manager.store(p1);
		   manager.commit();
	       Pessoa p2 = new Pessoa("Marcio");
	       manager.store(p2);
	       manager.commit();
	       Pessoa p3 = new Pessoa("Claudio");
	       manager.store(p3);
	       manager.commit();
	       Pessoa p4 = new Pessoa("Wagner");
	       manager.store(p4);
	       manager.commit();
	         
	       Reuniao r1 = new Reuniao("05/09/2023");
	       Reuniao r2 = new Reuniao("05/09/2023");
	       Reuniao r3 = new Reuniao("07/09/2023");
	       Reuniao r4 = new Reuniao("10/09/2023");
	       Reuniao r5 = new Reuniao("11/11/2023");
	       r1.setId(Util.gerarIReuniao());
	       manager.store(r1);
	       r2.setId(Util.gerarIReuniao());
	       manager.store(r2);
	       r3.setId(Util.gerarIReuniao());
	       manager.store(r3);    
	       r4.setId(Util.gerarIReuniao());
	       manager.store(r4);
	       r5.setId(Util.gerarIReuniao());
	       manager.store(r5);
	       manager.commit();
	       
	       r1.adicionarPessoa(p1);
	       r1.adicionarPessoa(p2);
	       r1.adicionarPessoa(p3);
	       r1.adicionarPessoa(p4);
	       r2.adicionarPessoa(p1);
	       r2.adicionarPessoa(p4);
	       r3.adicionarPessoa(p1);
	       r3.adicionarPessoa(p2);
	       r3.adicionarPessoa(p3);
	       r4.adicionarPessoa(p2);
	       r4.adicionarPessoa(p3);
	       r5.adicionarPessoa(p3);
	       manager.store(r1);
	       manager.store(r2);
	       manager.store(r3);
	       manager.commit();
	       System.out.println("Reunioes no banco");
	       System.out.println(r1.toString());
	       System.out.println(r2.toString());
	       System.out.println(r3.toString());
	       System.out.println(r4.toString());
	       System.out.println(r5.toString());
	       
	       Query q = manager.query();
	       q.constrain(Pessoa.class);
	       List<Pessoa> pessoas = q.execute();
	       System.out.println("Pessoas no banco");
	       for(Pessoa pessoa : pessoas) {
	    	   System.out.println(pessoa);
	       }	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Cadastrar();
	}
}
