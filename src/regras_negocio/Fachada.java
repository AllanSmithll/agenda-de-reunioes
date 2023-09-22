package regras_negocio;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOAluguel;
import daodb4o.DAOCarro;
import daodb4o.DAOCliente;
import daodb4o.DAOUsuario;
import modelo.Aluguel;
import modelo.Carro;
import modelo.Cliente;
import modelo.Usuario;

public class Fachada {
	private Fachada() {}

	private static DAOCarro daocarro = new DAOCarro();  
	private static DAOAluguel daoaluguel = new DAOAluguel(); 
	private static DAOCliente daocliente = new DAOCliente(); 
	private static DAOUsuario daousuario = new DAOUsuario(); 
	public static Usuario logado;	//contem o objeto Usuario logado em TelaLogin.java

	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}


	public static Carro cadastrarCarro(String placa, String modelo) throws Exception{
		DAO.begin();
		Carro carro = daocarro.read(placa);
		if (carro!=null)
			throw new Exception("carro ja cadastrado:" + placa);
		carro = new Carro(placa, modelo);

		daocarro.create(carro);
		DAO.commit();
		return carro;
	}

	public static Aluguel alugarCarro(String cpf, String placa, double diaria, String data1, String data2) throws Exception{
		DAO.begin();
		Carro car =  daocarro.read(placa);
		if(car==null) 
			throw new Exception ("carro incorreto para aluguel "+placa);
		if(car.isAlugado()) 
			throw new Exception ("carro ja esta alugado:"+placa);

		Cliente cli = daocliente.read(cpf);
		if(cli==null) 
			throw new Exception ("cliente incorreto para aluguel " + cpf);

		Aluguel aluguel = new Aluguel(data1,data2, diaria);
		aluguel.setCarro(car);
		aluguel.setCliente(cli);
		car.adicionar(aluguel);
		car.setAlugado(true);
		cli.adicionar(aluguel);

		daoaluguel.create(aluguel);
		daocarro.update(car);
		daocliente.update(cli);
		DAO.commit();
		return aluguel;
	}

	public static void devolverCarro(String placa) throws Exception{
		DAO.begin();
		Carro car =  daocarro.read(placa);
		if(car==null) 
			throw new Exception ("carro incorreto para devolucao");

		if(car.getAlugueis().isEmpty()) 
			throw new Exception ("carro nao pode ser devolvido - nao esta alugado");

		car.setAlugado(false);
		// obter o ultimo aluguel do carro
		Aluguel alug = car.getAlugueis().get(car.getAlugueis().size()-1);
		alug.setFinalizado(true);

		daocarro.update(car);
		DAO.commit();
	}

	public static void excluirCarro(String placa) throws Exception{
		DAO.begin();
		Carro car =  daocarro.read(placa);
		if(car==null) 
			throw new Exception ("carro incorreto para exclusao " + placa);

		if(! car.isAlugado()) 
			throw new Exception ("carro alugado nao pode ser excluido " + placa);


		//alterar os clientes dos alugueis do carro
		for (Aluguel a : car.getAlugueis()) {
			Cliente cli = a.getCliente();
			cli.remover(a);
			//atualizar o cliente no banco
			daocliente.update(cli);
			//apagar o aluguel
			daoaluguel.delete(a);
		}

		//apagar carro e seus alugueis em cascata
		daocarro.delete(car);
		DAO.commit();
	}

	public static Cliente cadastrarCliente(String nome, String cpf) throws Exception{
		DAO.begin();
		Cliente cli = daocliente.read(cpf);
		if (cli!=null)
			throw new Exception("Pessoa ja cadastrado:" + cpf);
		cli = new Cliente(nome, cpf);

		daocliente.create(cli);
		DAO.commit();
		return cli;
	}
	public static void excluirCliente(String cpf) throws Exception{
		DAO.begin();
		Cliente cli =  daocliente.read(cpf);
		if(cli==null) 
			throw new Exception ("cliente incorreto para exclusao " + cpf);

		if(!cli.getAlugueis().isEmpty()) {
			List<Aluguel> alugueis = cli.getAlugueis();
			Aluguel ultimo = alugueis.get(alugueis.size()-1);
			if(ultimo !=null && !ultimo.isFinalizado()) 
				throw new Exception ("Nao pode excluir cliente com carro alugado: " + cpf);
		}
		
		//alterar os carros dos alugueis 
		for (Aluguel a : cli.getAlugueis()) {
			Carro car = a.getCarro();
			car.remover(a);
			daocarro.update(car);
			daoaluguel.delete(a);
		}

		//apagar carro e seus alugueis em cascata
		daocliente.delete(cli);
		DAO.commit();
	}

	public static void excluirAluguel(int id) throws Exception{
		DAO.begin();
		Aluguel aluguel =  daoaluguel.read(id);
		if(aluguel==null) 
			throw new Exception ("aluguel incorreto para exclusao " + id);

		if(! aluguel.isFinalizado()) 
			throw new Exception ("aluguel nao finalizado nao pode ser excluido " + id);

		//alterar os clientes dos alugueis do carro
		Cliente cli = aluguel.getCliente();
		Carro car = aluguel.getCarro();
		cli.remover(aluguel);
		car.remover(aluguel);

		daocliente.update(cli);
		daocarro.update(car);
		daoaluguel.delete(aluguel);
		DAO.commit();
	}

	public static List<Cliente>  listarClientes(){
		DAO.begin();
		List<Cliente> resultados =  daocliente.readAll();
		DAO.commit();
		return resultados;
	} 

	public static List<Carro>  listarCarros(){
		DAO.begin();
		List<Carro> resultados =  daocarro.readAll();
		DAO.commit();
		return resultados;
	}

	public static List<Aluguel> listarAlugueis(){
		DAO.begin();
		List<Aluguel> resultados =  daoaluguel.readAll();
		DAO.commit();
		return resultados;
	}

	public static List<Usuario>  listarUsuarios(){
		DAO.begin();
		List<Usuario> resultados =  daousuario.readAll();
		DAO.commit();
		return resultados;
	} 

	public static List<Aluguel> alugueisModelo(String modelo){	
		DAO.begin();
		List<Aluguel> resultados =  daoaluguel.alugueisModelo(modelo);
		DAO.commit();
		return resultados;
	}

	public static List<Aluguel> alugueisFinalizados(){	
		DAO.begin();
		List<Aluguel> resultados =  daoaluguel.alugueisFinalizados();
		DAO.commit();
		return resultados;
	}

	public static List<Carro>  carrosNAlugueis(int n){	
		DAO.begin();
		List<Carro> resultados =  daocarro.carrosNAlugueis(n);
		DAO.commit();
		return resultados;
	}

	public static Carro localizarCarro(String placa){
		return daocarro.read(placa);
	}
	public static Cliente localizarCliente(String cpf){
		return daocliente.read(cpf);
	}

	
	//------------------Usuario------------------------------------
	public static Usuario cadastrarUsuario(String nome, String senha) throws Exception{
		DAO.begin();
		Usuario usu = daousuario.read(nome);
		if (usu!=null)
			throw new Exception("Usuario ja cadastrado:" + nome);
		usu = new Usuario(nome, senha);

		daousuario.create(usu);
		DAO.commit();
		return usu;
	}
	public static Usuario localizarUsuario(String nome, String senha) {
		Usuario usu = daousuario.read(nome);
		if (usu==null)
			return null;
		if (! usu.getSenha().equals(senha))
			return null;
		return usu;
	}
}
