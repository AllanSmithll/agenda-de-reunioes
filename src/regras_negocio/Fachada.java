package regras_negocio;

/**********************************
 * Fachada class
 * @authors Allan Amancio e Marcio Jose
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOPessoa;
import daodb4o.DAOReuniao;
import daodb4o.DAOUsuario;
import modelo.Pessoa;
import modelo.Reuniao;
import modelo.Usuario;

public class Fachada {
	private Fachada() {
	}

	private static DAOReuniao daoreuniao = new DAOReuniao();
	private static DAOPessoa daopessoa = new DAOPessoa();
	private static DAOUsuario daousuario = new DAOUsuario();
	public static Usuario logado; // contem o objeto Usuario logado em TelaLogin.java

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}
	// ------------------Reuniao------------------------------------
	public static Reuniao cadastrarReuniao(String data) throws Exception {
		DAO.begin();
		if (data.equals(null) || data.isEmpty()) {
			throw new Exception("A data da reuniao nao pode ser vazia.");
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataReuniao = LocalDate.parse(data, formatter);
		LocalDate dataAtual = LocalDate.now();
		if (dataReuniao.isBefore(dataAtual)) {
			throw new Exception("A data da reuniao deve ser para hoje ou no futuro.");
		}
		Reuniao reuniao = new Reuniao(data);

		try {
			daoreuniao.create(reuniao);
		} catch (Exception e) {
			throw new Exception("Erro ao cadastrar a reuniao: " + e.getMessage());
		}
		DAO.commit();
		return reuniao;
	}

	public static void excluirReuniao(int id) throws Exception {
		DAO.begin();
		Reuniao reuniaoSendoExcluida = daoreuniao.read(id);
		if(reuniaoSendoExcluida == null) {
			throw new Exception("Reuniao ja excluida do nosso banco de dados!");
		}
		for(Pessoa pessoa : reuniaoSendoExcluida.getListaDePessoas()) {
			pessoa.removerReuniao(reuniaoSendoExcluida);
			reuniaoSendoExcluida.removerPessoa(pessoa);
			daopessoa.update(pessoa);
		}
		daoreuniao.delete(reuniaoSendoExcluida);
		DAO.commit();
	}
	
	public static List<Reuniao>  listarReunioes(){
		DAO.begin();
		List<Reuniao> resultados =  daoreuniao.readAll();
		DAO.commit();
		return resultados;
	} 
	public static Reuniao localizarReuniao(int id){
		return daoreuniao.read(id);
	}

	// ------------------Pessoa------------------------------------
	public static Pessoa cadastrarPessoa(String nome) throws Exception {
		DAO.begin();
		if (nome.equals(null) || nome.isEmpty()) {
			throw new Exception("O nome da pessoa nao pode ser vazio.");
		}
		Pessoa pessoaSendoCadastrada = daopessoa.read(nome);
		if (pessoaSendoCadastrada != null) {
			throw new Exception("Pessoa ja foi cadastrada!");
		}
		pessoaSendoCadastrada = new Pessoa(nome);
		try {
			daopessoa.create(pessoaSendoCadastrada);
		} catch(Exception e) {
			throw new Exception("Erro ao cadastrar pessoa: " + e.getMessage());
		}
		DAO.commit();
		return pessoaSendoCadastrada;
	}

	public static void excluirPessoa(String nome) throws Exception {
		DAO.begin();
		Pessoa pessoaSendoExcluida = daopessoa.read(nome);
		if (pessoaSendoExcluida == null) {
<<<<<<< Updated upstream
			throw new Exception("Pessoa ja foi excluida do nosso banco de dados!");
=======
			throw new Exception("Pessoa inexistente!");
>>>>>>> Stashed changes
		}
		// Removendo o relacionamento antes da exclusão
		for (Reuniao reuniao : pessoaSendoExcluida.getReunioes()) {
			pessoaSendoExcluida.removerReuniao(reuniao);
			reuniao.removerPessoa(pessoaSendoExcluida);
			daoreuniao.update(reuniao);
		}
		daopessoa.delete(pessoaSendoExcluida);
		DAO.commit();

	}
	
	public static List<Pessoa>  listarPessoas(){
		DAO.begin();
		List<Pessoa> resultados =  daopessoa.readAll();
		DAO.commit();
		return resultados;
	} 
	
	public static Pessoa localizarPessoa(String nome){
		return daopessoa.read(nome);
	}


	// ------------------Usuario------------------------------------
	public static Usuario cadastrarUsuario(String nome, String senha) throws Exception {
		DAO.begin();
		Usuario usu = daousuario.read(nome);
		if (usu != null)
			throw new Exception("Usuario ja cadastrado:" + nome);
		usu = new Usuario(nome, senha);

		daousuario.create(usu);
		DAO.commit();
		return usu;
	}

	public static Usuario localizarUsuario(String nome, String senha) {
		Usuario usu = daousuario.read(nome);
		if (usu == null)
			return null;
		if (!usu.getSenha().equals(senha))
			return null;
		return usu;
	}
	
	public static List<Usuario>  listarUsuarios(){
		DAO.begin();
		List<Usuario> resultados =  daousuario.readAll();
		DAO.commit();
		return resultados;
	}
	
	public static List<Reuniao> listarReunioesNaData(String data){	
		DAO.begin();
		List<Reuniao> resultados =  daoreuniao.listarReunioesNaData(data);
		DAO.commit();
		return resultados;
	}

	public static List<Carro>  carrosNAlugueis(int n){	
		DAO.begin();
		List<Carro> resultados =  daocarro.carrosNAlugueis(n);
		DAO.commit();
		return resultados;
	}
	
	public static List<Aluguel> alugueisFinalizados(){	
		DAO.begin();
		List<Aluguel> resultados =  daoaluguel.alugueisFinalizados();
		DAO.commit();
		return resultados;
	}
}
