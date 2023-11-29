/*
 * Cadastrar class
 * @authors Allan Amancio e Marcio Jose
 */
package appsConsole;

import java.util.Arrays;
import java.util.List;

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
			List<String> pessoas = Arrays.asList("Allan", "Márcio", "Claudio", "Wagner");
			List<String> pessoas1 = Arrays.asList("Allan", "Wagner");
			List<String> pessoas2 = Arrays.asList("Allan", "Marcio", "Claudio");
			List<String> pessoas3 = Arrays.asList("Marcio", "Claudio");
			List<String> pessoas4 = Arrays.asList("Allan", "Claudio");

			Fachada.adicionarPessoasAReuniao(pessoas, 1);
			Fachada.adicionarPessoasAReuniao(pessoas1, 2);
			Fachada.adicionarPessoasAReuniao(pessoas2, 3);
			Fachada.adicionarPessoasAReuniao(pessoas3, 4);
			Fachada.adicionarPessoasAReuniao(pessoas4, 5);
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
