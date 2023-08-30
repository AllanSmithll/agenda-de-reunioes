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
	         
	       Reuniao r1 = new Reuniao("10/10/2023");
	       Reuniao r2 = new Reuniao("12/10/2023");
	       r1.setId(Util.gerarIReuniao());
	       manager.store(r1);
	       r2.setId(Util.gerarIReuniao());
	       manager.store(r2);
	       manager.commit();
	       
	       r1.adicionarPessoa(p1);
	       r1.adicionarPessoa(p2);
	       r1.adicionarPessoa(p3);
	       r1.adicionarPessoa(p4);
	       r2.adicionarPessoa(p1);
	       r2.adicionarPessoa(p4);
	       manager.store(r1);
	       manager.store(r2);
	       manager.commit();
	       System.out.println(r1.toString());
	        
	       
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
