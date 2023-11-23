/*
 * Cadastrar class
 * @authors Allan Amancio e Marcio Jose
 */
package appsConsole;

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar() {
		try {
		   Fachada.inicializar();
		   System.out.println("Cadastrando pessoas");
           Fachada.cadastrarPessoa("Allan");
           Fachada.cadastrarPessoa("Marcio");
           Fachada.cadastrarPessoa("Claudio");
           Fachada.cadastrarPessoa("Wagner");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
		   System.out.println("Cadastrando reunioes");
           Fachada.cadastrarReuniao("05/12/2024");
           Fachada.cadastrarReuniao("05/12/2024");
           Fachada.cadastrarReuniao("07/12/2024");
           Fachada.cadastrarReuniao("10/12/2024");
           Fachada.cadastrarReuniao("11/12/2024");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
		   System.out.println("Agendando reunioes");
           Fachada.agendarReuniao("Allan", 1);
           Fachada.agendarReuniao("Marcio", 1);
           Fachada.agendarReuniao("Claudio", 1);
           Fachada.agendarReuniao("Wagner", 1);
           Fachada.agendarReuniao("Allan", 2);
           Fachada.agendarReuniao("Wagner", 2);
           Fachada.agendarReuniao("Allan", 3);
           Fachada.agendarReuniao("Marcio", 3);
           Fachada.agendarReuniao("Claudio", 3);
           Fachada.agendarReuniao("Marcio", 4);
           Fachada.agendarReuniao("Claudio", 4);
           Fachada.agendarReuniao("Allan", 5);
           Fachada.agendarReuniao("Claudio", 5);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("\nfim do programa!");
	}

	public static void main(String[] args) {
		new Cadastrar();
	}
}
