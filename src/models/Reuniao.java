/*
 * Reuniao class
 * @authors Allan Amancio e Marcio Jose
 */
package models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
@Entity
public class Reuniao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String data;
	
	@ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
	private List<Pessoa> listaDePessoas = new ArrayList<>();
	
	public Reuniao() {}
	
	public Reuniao(String dataDaReuniao) {
		data = dataDaReuniao;
	}

	public int getId() { return id;	}
	public void setId(int id) {	this.id = id; }
	public String getData() { return data; }
	public void setData(String data) { this.data = data; }
	public List<Pessoa> getListaDePessoas() { return listaDePessoas; }
	public boolean getParticipanteReuniao(Pessoa pessoa) {
		for (Pessoa p : listaDePessoas) {
			if (p.equals(pessoa)) return true;
		}
		return false;
	}

	public void adicionarPessoa(Pessoa pessoa) throws Exception {
		if (this.getParticipanteReuniao(pessoa)) {
			throw new Exception("Essa pessoa ja esta na reuniao: "+pessoa.getNome());
		}
		this.listaDePessoas.add(pessoa);
		pessoa.adicionarReuniao(this);
	}

	public void removerPessoa(Pessoa pessoa) throws Exception {
		this.listaDePessoas.remove(pessoa);

	}

	public int numeroPessoasDaReuniao() {
		return this.listaDePessoas.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Reuniao reuniao = (Reuniao) obj;
		return id == reuniao.id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Reuniao: id=").append(id).append(", data=").append(data).append("\n" + " Pessoas:");
		if (listaDePessoas.isEmpty()) {
			sb.append("Nenhuma pessoa agendada");
		} else {
			sb.append("\n");
			for (Pessoa p : listaDePessoas) {
				sb.append(" - ").append(p.getNome()).append("\n");
			}
		}

		return sb.toString();
	}
}
