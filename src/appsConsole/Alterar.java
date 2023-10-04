package appsConsole;

import appsConsole.AlterarFachada;
import regras_negocio.Fachada;

public class Alterar {
	
	public Alterar() {
		try {
			Fachada.inicializar();
			Fachada.removerPessoaDaReuniao("Marcio", 1);
			System.out.println("Pessoa Márcio removido da reuniao com id 1 ");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Alterar();
	}
}
