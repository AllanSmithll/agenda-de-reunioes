/*
  * Listar class
 * @authors Allan Amancio e Marcio Jose
 */
package appsConsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Pessoa;
import modelo.Reuniao;

 class Listar {
	protected ObjectContainer manager;

	public Listar() {
		try {
			manager = Util.conectarBanco();
			
			System.out.println("\n---listagem de Pessoa:");
			Query q = manager.query();
			q.constrain(Pessoa.class);  				
			List<Pessoa> listaDePessoas = q.execute();
			for(Pessoa pessoa: listaDePessoas) {
				System.out.println(pessoa);
			}
			System.out.println("\n---listagem de Reunioes:");
			q = manager.query();
			q.constrain(Reuniao.class);  				
			List<Reuniao> listaDeReunioes = q.execute();
			
			for(Reuniao reuniao: listaDeReunioes) {
				System.out.println(reuniao);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Listar();
	}
}
