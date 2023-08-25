package appsConsole;


import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Pessoa;
import models.Reuniao;

 class Listar {
	protected ObjectContainer manager;

	public Listar() {
		try {
			manager = Util.conectarBanco();
			
			System.out.println("\n---listagem de Pessoa:");
			Query q = manager.query();
			q.constrain(Pessoa.class);  				
			List<Pessoa> listaDePessoas = q.execute();
			for(Pessoa pessoa: listaDePessoas)
				System.out.println(pessoa);

			System.out.println("\n---listagem de Reuniaos:");
			q = manager.query();
			q.constrain(Reuniao.class);  				
			List<Reuniao> listDeReunioes = q.execute();
			for(Reuniao reuniao: listDeReunioes)
				System.out.println(reuniao);
			
		

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Listar();
	}
}
