/*
 * Pessoa class
 * @authors Allan Amancio e Marcio Jose
 */
package appsConsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.*;

public class Alterar {
	protected ObjectContainer manager;
	
	public Alterar() {
		try {
			manager = Util.conectarBanco();
			System.out.println("Tirando pessoa da reuniao");
			
			// Consultando Reuniao
			Query q1 = manager.query();
			q1.constrain(Reuniao.class);  				
			q1.descend("id").constrain(1);		 
			List<Reuniao> resultados1 = q1.execute(); 
			
			// Consultando Pessoa
			Query q2 = manager.query();
			q2.constrain(Pessoa.class);  				
			q2.descend("nome").constrain("Wagner");		 
			List<Pessoa> resultados2 = q2.execute();
			
			if (resultados2.size() == 0)
				throw new Exception("Pessoa inexistente no banco."); // Pessoa inexistente
			
			// Remover pessoa da reuniao
			if(resultados1.size()>0 && resultados2.size()>0) {
				Reuniao reuniao = resultados1.get(0);
				Pessoa pessoa = resultados2.get(0);
				
				reuniao.removerPessoa(pessoa);
				manager.store(reuniao);
				manager.store(pessoa);
				manager.commit();
				System.out.println("Pessoa " + pessoa.getNome() + " removida da reunião " + reuniao.getId()+".");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Alterar();
	}
}
