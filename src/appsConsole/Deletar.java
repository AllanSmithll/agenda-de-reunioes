/*
 * Deletar class
 * @authors Allan Amancio e Marcio Jose
 */
package appsConsole;


import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Pessoa;
import models.Reuniao;
import regras_negocio.Fachada;


public class Deletar {
	protected ObjectContainer manager;

	public Deletar() {
		try {
			Fachada.inicializar();
			
			//Excluindo a pessoa Márcio
			Fachada.excluirPessoa("Marcio");
			System.out.println("Pessoa excluida");
			
					
			}
		 catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Deletar();
		
	}
}
