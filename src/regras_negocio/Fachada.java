package regras_negocio;

/**********************************
 * Fachada class
 * @authors Allan Amancio e Marcio Jose
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;



import daojpa.DAO;
import daojpa.DAOPessoa;
import daojpa.DAOReuniao;
import daojpa.DAOUsuario;

import models.Pessoa;
import models.Reuniao;
import models.Usuario;

public class Fachada {
	private Fachada() {
	}

	private static DAOReuniao daoreuniao = new DAOReuniao();
	private static DAOPessoa daopessoa = new DAOPessoa();
	private static DAOUsuario daousuario = new DAOUsuario();
	public static Usuario logado;

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
			throw new Exception(" aqui A data da reuniao deve ser para hoje ou no futuro.");
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
		if (reuniaoSendoExcluida == null) {
			throw new Exception("Reuniao ja excluida do nosso banco de dados!");
		}
		for (Pessoa pessoa : reuniaoSendoExcluida.getListaDePessoas()) {
			pessoa.removerReuniao(reuniaoSendoExcluida);
			reuniaoSendoExcluida.removerPessoa(pessoa);
			daopessoa.update(pessoa);
		}
		daoreuniao.delete(reuniaoSendoExcluida);
		DAO.commit();
	}

	public static List<Reuniao> listarReunioes() {
		DAO.begin();
		List<Reuniao> resultados = daoreuniao.readAll();
		DAO.commit();
		return resultados;
	}

	public static Reuniao localizarReuniao(int id) {
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
			DAO.rollback();
			throw new Exception("Pessoa ja foi cadastrada!");
		}
		pessoaSendoCadastrada = new Pessoa(nome);
		
		try {
			daopessoa.create(pessoaSendoCadastrada);
		} catch (Exception e) {
			DAO.rollback();
			throw new Exception("Erro ao cadastrar pessoa: " + e.getMessage());
		}
		DAO.commit();
		return pessoaSendoCadastrada;
	}

	public static void excluirPessoa(String nome) throws Exception {
		DAO.begin();
		Pessoa pessoaSendoExcluida = daopessoa.read(nome);
		if (pessoaSendoExcluida == null) {
			throw new Exception("Pessoa inexistente!");
		}
		// Removendo o relacionamento antes da exclusão
		for (Reuniao reuniao : pessoaSendoExcluida.getReunioes()) {
			reuniao.removerPessoa(pessoaSendoExcluida);
			daoreuniao.update(reuniao);
		}
		daopessoa.update(pessoaSendoExcluida);
		daopessoa.delete(pessoaSendoExcluida);
		DAO.commit();

	}

	public static List<Pessoa> listarPessoas() {
		DAO.begin();
		List<Pessoa> resultados = daopessoa.readAll();
		DAO.commit();
		return resultados;
	}

	public static Pessoa localizarPessoa(String nome) {
		return daopessoa.read(nome);
	}
	public static void removerPessoaDaReuniao(String nome, int idReuniao) throws Exception {
		
		
		
		Reuniao reuniao = Fachada.localizarReuniao(idReuniao);
		if(reuniao == null) {
			throw new Exception("Reunião inexistente!");
		}
		Pessoa pessoa = Fachada.localizarPessoa(nome);
		if(pessoa == null) {
			throw new Exception("Pessoa inexistente!");
		}
		
		reuniao.removerPessoa(pessoa);
		pessoa.removerReuniao(reuniao);
		
		daoreuniao.update(reuniao);
		daopessoa.update(pessoa);
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

	public static List<Usuario> listarUsuarios() {
		DAO.begin();
		List<Usuario> resultados = daousuario.readAll();
		return resultados;
	}
	
	// ------------------Consultas------------------------------------
	public static List<Reuniao> listarReunioesNaData(String data) {
		DAO.begin();
		List<Reuniao> resultados = daoreuniao.listarReunioesNaData(data);
		DAO.commit();
		return resultados;
	}

	public static List<Reuniao> listarReunioesComAPessoa(String nome) {
		DAO.begin();
		List<Reuniao> resultados = daoreuniao.listarReunioesComAPessoa(nome);
		DAO.commit();
		return resultados;
	}
	
	public static List<Pessoa> pessoasEmMaisDeNReunioes(int quantidade){	
		DAO.begin();
		List<Pessoa> resultados = daopessoa.listarPessoasEmMaisDeNReunioes(quantidade);
		DAO.commit();
		return resultados;
	}
	public static void agendarReuniao(String nomeDaPessoa, int IdReuniao) throws Exception {
		DAO.begin();
		Pessoa pessoa = Fachada.localizarPessoa(nomeDaPessoa);
		Reuniao reuniao = Fachada.localizarReuniao(IdReuniao);
		
		if(pessoa == null) {
			throw new Exception("pessoa nao encontrada!");
		}
		
		if(reuniao == null) {
			throw new Exception("reuniao nao encontrada!");
		}
		
		reuniao.adicionarPessoa(pessoa);
		daoreuniao.update(reuniao);
		daopessoa.update(pessoa);
		DAO.commit();	
	}
}
