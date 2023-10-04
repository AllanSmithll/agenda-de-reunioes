/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appsConsole;

import java.util.List;

import models.Pessoa;
import models.Reuniao;
import regras_negocio.Fachada;

public class Consultar {

	public Consultar() {
		try {
			Fachada.inicializar();

			System.out.println("Consultas na Agenda de Reunioes... \n");
			System.out.println("\n1- Quais as reunioes na data X?");
			System.out.println("Data escolhida: 05/09/2023");

			for (Reuniao r : Fachada.listarReunioesNaData("05/09/2023")) {
				System.out.println(r);
			}

			System.out.println("\n2- Quais as reunioes com a pessoa de nome X?");
			System.out.println("Nome escolhido: Allan");

			for (Reuniao r : Fachada.listarReunioesComAPessoa("Allan")) {
				System.out.println(r);
			}

			System.out.println("\n3- Quais as pessoas que tem mais de N reunioes?");
			System.out.println("Quantidade escolhida: 2");

			for (Pessoa p : Fachada.pessoasEmMaisDeNReunioes(2)) {
				System.out.println(p);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Consultar();
	}
}
