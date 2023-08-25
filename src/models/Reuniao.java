/*
 * Reuniao class
 * @authors Allan Amancio e Marcio Jose
 */
package models;

import java.util.ArrayList;
import java.util.List;

public class Reuniao {
	private int id;
	private String data;
	private List<Pessoa> listaDePessoas = new ArrayList<>();
	
	public Reuniao(String dataDaReuniao) {
		data = dataDaReuniao;
	}

	public int getId() { return id;	}
	public void setId(int id) {	this.id = id; }
	public String getData() {return data; }
	public void setData(String data) { this.data = data; }
	public List<Pessoa> getListaDePessoas() { return listaDePessoas; }
	
	public void adicionarPessoa(Pessoa pessoa) {
		this.listaDePessoas.add(pessoa);
		pessoa.adicionarReuniao(this);
	}
	
	public void removerPessoa(Pessoa pessoa) {
		this.listaDePessoas.removeIf(p -> p.equals(pessoa));
		pessoa.removerReuniao(this);
	}
	
	public int numeroPessoasDaReuniao() {
		return this.listaDePessoas.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
        sb.append("Reuniao: id=").append(id).append(", data=").append(data)
        .append("\n"+" Pessoas:");
        if(listaDePessoas.isEmpty()) {
            sb.append("Nenhuma pessoa agendada");
        } else {
            sb.append("\n");
            for (Pessoa p : listaDePessoas) {
                sb.append(" * ").append(p.getNome()).append("\n");
            }
        }
        
        return sb.toString();	
    }	
}
