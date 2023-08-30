package appsConsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import models.Pessoa;
import models.Reuniao;

public class Consultar {
    protected ObjectContainer manager;

	public Consultar() {
		try {
			manager = Util.conectarBanco();
			
			System.out.println("Consultas na Agenda de Reunioes... \n");
			System.out.println("\n1- Quais as reunioes na data X?");
			System.out.println("Data escolhida: 05/09/2023");
			Query q;
			q = manager.query();
			q.constrain(Reuniao.class);
			q.descend("data").constrain("05/09/2023");
			List<Reuniao> reunioes = q.execute();
			for(Reuniao r : reunioes)
				System.out.println(r);
			
			
			System.out.println("\n2- Quais as reunioes com a pessoa de nome X?");
			System.out.println("Nome escolhido: Allan");
			q = manager.query();
			q.constrain(Reuniao.class);
			q.descend("listaDePessoas").descend("nome").constrain("Allan");
			List<Reuniao> reunioes_pessoa = q.execute();
			for(Reuniao r : reunioes_pessoa)
				System.out.println(r);
			
			System.out.println("\n3- Quais as pessoas que tem mais de N reunioes?");
			q = manager.query();
			q.constrain(Pessoa.class);
			q.constrain(new Filtro1());
			List<Pessoa> pessoas = q.execute();
			for(Pessoa p :pessoas)
				System.out.println(p);
//			
//			System.out.println("\nCarros que possuem 1 alugueis");
//			q = manager.query();
//			q.constrain(Carro.class);
//			q.constrain(new Filtro1());
//			List<Carro> resultados4 = q.execute();
//			for(Carro c : resultados4)
//				System.out.println(c);
			
			
			//System.out.println("\nClientes que possuem 2 alugueis");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Consultar();
	}
}

// class interna
@SuppressWarnings("serial")
class Filtro1 implements Evaluation {
	public void evaluate(Candidate candidate) {
		Pessoa p = (Pessoa) candidate.getObject();
		int quantidade = 2;
		if(p.getReunioes().size() > quantidade) 
			candidate.include(true);
		else		
			candidate.include(false);
	}
}
