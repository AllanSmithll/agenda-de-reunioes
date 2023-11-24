/*
  * Pessoa class
 * @authors Allan Amancio e Marcio Jose
 */
package models;

import java.util.List;

import daojpa.LowerToUpperConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;


import java.util.ArrayList;

@Entity
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Convert(converter=LowerToUpperConverter.class)
    private String nome;
    
    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    private List<Reuniao> reunioes = new ArrayList<>();
    
    public Pessoa (){}
    
    public Pessoa(String nome) {
    	
        this.nome = nome;
    }
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getNome() { return this.nome;}
	public void setNome(String nome) { this.nome = nome; }
	public List<Reuniao> getReunioes() { return this.reunioes; }
	public void setReunioes(List<Reuniao> reunioes) { this.reunioes = reunioes; }
	public Reuniao getReuniao(Reuniao r) {
		for(Reuniao reu : reunioes) {
			if (reu.equals(r))
				return reu;
		}
		return null;
	}
	
	public void adicionarReuniao(Reuniao r) { this.reunioes.add(r);	}
	
	public void removerReuniao(Reuniao r) {	this.reunioes.remove(r); }
	
	public int numeroReunioes() { return this.reunioes.size(); }
	
//	@Override
//	public boolean equals(Object obj) {
//	    if (this == obj) {
//	        return true;
//	    }
//	    if (obj == null || getClass() != obj.getClass()) {
//	        return false;
//	    }
//	    Pessoa pessoa = (Pessoa) obj;
//	    return this.nome.equals(pessoa.nome);
//	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pessoa: nome=").append(nome).append(", reunioes=");
        if (reunioes.isEmpty()) {
            sb.append(" Nenhuma reuniao agendada");
        } else {
            sb.append("\n");
            for (Reuniao r : reunioes) {
            	if(r != null) {
                sb.append(" # ").append("ID:" + r.getId() + " Data: " + r.getData()).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
