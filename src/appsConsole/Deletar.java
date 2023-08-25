/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appsConsole;


import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import models.Pessoa;
import models.Reuniao;


public class Deletar {
	protected ObjectContainer manager;

	public Deletar() {
		try {
			manager = Util.conectarBanco();
			System.out.println("excluindo");
			
			//localizar a pessoa chamada Marcio
			Query q = manager.query();
			q.constrain(Pessoa.class);  				
			q.descend("nome").constrain("Marcio");		 
			List<Pessoa> resultados = q.execute(); // select p from Pessoa p where p.nome="Marcio"
			
			if(resultados.size()>0) {
				Pessoa marcio = resultados.get(0);
				//Deletando todas reunioes de marcio
				for(Reuniao reuniaoAtual : marcio.getReunioes()) {
					reuniaoAtual.removerPessoa(marcio);
						manager.store(reuniaoAtual);
						manager.delete(marcio);
						manager.commit();
					}
				}
			System.out.println("Marcio foi excluido do sistema");
			
//			Query q2 = manager.query();
//			q.constrain(Reuniao.class);  					 
//			List<Reuniao> reunioes = q.execute(); 
//			System.out.println(reunioes.get(0));
//			if(reunioes.size() > 0) {
//				Reuniao reuniao2 = reunioes.get(0);
//				
//				for(Pessoa pessoaAtual : reuniao2.getListaDePessoas()) {
//					if(pessoaAtual.getNome().equals("Wagner")) {
//						reuniao2.removerPessoa(pessoaAtual);
//						pessoaAtual.removerReuniao(reuniao2);
//						manager.store(pessoaAtual);
//						manager.delete(pessoaAtual);
//						manager.store(reuniao2);
//						manager.commit();
//					}
//				}
//			}
			
			
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
