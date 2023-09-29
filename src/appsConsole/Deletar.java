/*
 * Deletar class
 * @authors Allan Amancio e Marcio Jose
 */
package appsConsole;


import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Pessoa;
import modelo.Reuniao;


public class Deletar {
	protected ObjectContainer manager;

	public Deletar() {
		try {
			manager = Util.conectarBanco();
			System.out.println("excluindo");
			
			Query queryReuniao = manager.query();
			queryReuniao.constrain(Reuniao.class);  							 
			List<Reuniao> reunioes = queryReuniao.execute(); 
			
			Query queryPessoa = manager.query();
			queryPessoa.constrain(Pessoa.class); 
			queryPessoa.descend("nome").constrain("Marcio");
			List<Pessoa> pessoas = queryPessoa.execute(); // select p from Pessoa p where p.nome="Marcio"
			
			Pessoa marcio = pessoas.get(0);
	
			//Removendo todas as reunioes agendadas da pessoa Marcio,
			//e caso a reuniao não tenha mais membros ela sera deletada
			if(pessoas.size() > 0) {
				for (Reuniao reuniao : reunioes) {
					if(reuniao.getParticipanteReuniao(marcio)) {
						reuniao.removerPessoa(marcio);
						manager.store(reuniao);	
						marcio.removerReuniao(reuniao);
						manager.store(marcio);;
					} 
					
				}
			}
			
			//Deletando todas as reuniões orfãos
			for (Reuniao reuniao : reunioes) {
				if(reuniao.numeroPessoasDaReuniao() < 2) {
					manager.delete(reuniao);
					
				}
			}

			manager.commit();
			}
		 catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Deletar();
		
	}
}
