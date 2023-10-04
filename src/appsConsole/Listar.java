/*
  * Listar class
 * @authors Allan Amancio e Marcio Jose
 */
package appsConsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Pessoa;
import models.Reuniao;
import regras_negocio.Fachada;

 class ListarFachada {
	protected ObjectContainer manager;

	public Listar() {
		try {
			Fachada.inicializar();
			
			System.out.println("\n---listagem de Pessoa:");
	
			
			for(Pessoa pessoa: Fachada.listarPessoas()) {
				System.out.println(pessoa);
			}
					
			System.out.println("\n---listagem de Reuniões:");
			
			for(Reuniao reuniao: Fachada.listarReunioes()) {
				System.out.println(reuniao);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Listar();
	}
}
