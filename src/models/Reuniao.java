package models;

import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import modelo.Aluguel;

public class Reuniao {
	private int id;
	private String data;
	private List<Pessoa> listaDePessoas = new ArrayList<>();
	
	public Reuniao(String dataDaReuniao) {
		data = dataDaReuniao;
	}
	
	
	public void adicionarPessoa(Pessoa pessoa) {
		listaDePessoas.add(pessoa);
	}
	
	public void removerPessoa(Pessoa pessoa) {
		listaDePessoas.remove(pessoa);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<Pessoa> getListaDePessoas() {
		return listaDePessoas;
	}

	@Override
	public String toString() {
		String pessoasNaReuniao = "";
		for(Pessoa a : listaDePessoas) {
			pessoasNaReuniao += a.getNome() + ",";
		}
		return "Reuniao: id=" + id + ", data=" + data + "Membros: " + pessoasNaReuniao;
	}

	
	
	
}
