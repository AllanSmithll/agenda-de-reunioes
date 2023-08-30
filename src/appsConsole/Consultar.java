package appsConsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import models.Reuniao;

public class Consultar {
    protected ObjectContainer manager;

	public Consultar() {
		try {
			manager = Util.conectarBanco();
			
			System.out.println("Consultas na Agenda de Reunioes... \n");
			System.out.println("\n1- Quais as reunioes na data X?");
			Query q;
			q = manager.query();
			q.constrain(Reuniao.class);
			q.descend("data").constrain("12/10/2023");
			List<Reuniao> reunioes = q.execute();
			for(Reuniao a : reunioes)
				System.out.println(a);
			
			
//			System.out.println("\nAlugueis nao finalizados");
//			q = manager.query();
//			q.constrain(Aluguel.class);
//			q.descend("finalizado").constrain(false);
//			List<Aluguel> resultados2 = q.execute();
//			for(Aluguel a : resultados2)
//				System.out.println(a);
//			
//			System.out.println("\nCarros que possuem alugueis de 9 dias");
//			q = manager.query();
//			q.constrain(Carro.class);
//			q.descend("alugueis").descend("dias").constrain(9);
//			List<Carro> resultados3 = q.execute();
//			for(Carro c : resultados3)
//				System.out.println(c);
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

//classe interna
//class Filtro1 implements Evaluation {
//	public void evaluate(Candidate candidate) {
//		Carro car = (Carro) candidate.getObject();
//		if(car.getAlugueis().size()== 1) 
//			candidate.include(true); 
//		else		
//			candidate.include(false);
//	}
//}
//
//class Filtro2 implements Evaluation {
//	public void evaluate(Candidate candidate) {
//		Carro car = (Carro) candidate.getObject();
//		boolean finalizado = true;
//		for(Aluguel a : car.getAlugueis())
//			if(!a.isFinalizado())
//				finalizado=false;
//				
//		if(finalizado)
//			candidate.include(true);
//		else		
//			candidate.include(false);
//	}
//}
