package appsConsole;

import com.db4o.ObjectContainer;

import models.Pessoa;
import models.Reuniao;

public class Cadastrar {
	protected ObjectContainer manager;

	public Cadastrar() {
		try {
			manager = Util.conectarBanco();

//			System.out.println("cadastrando...");
//			Carro carro1 = new Carro("AAA1000", "palio");
//			manager.store(carro1);
//			manager.commit();
//			
//			Carro carro2 = new Carro("BBB2000", "onix");
//			manager.store(carro2);
//			manager.commit();
//			
//			Carro carro3 = new Carro("CCC3000", "civic");
//			manager.store(carro3);
//			manager.commit();
//
//			Cliente cli1 = new Cliente("joao", "1111");
//			manager.store(cli1);
//			manager.commit();
//			
//			Cliente cli2 = new Cliente("maria", "2222");
//			manager.store(cli2);
//			manager.commit();
//			
//			Cliente cli3 = new Cliente("jose", "3333");
//			manager.store(cli3);
//			manager.commit();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Util.desconectar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Cadastrar();
	}
}
