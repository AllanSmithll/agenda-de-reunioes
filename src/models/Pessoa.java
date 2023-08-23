/*
 * Pessoa class
 * @authors Allan Amancio e Marcio Jose
 */
package models;

import java.util.ArrayList;

public class Pessoa {
    private String nome;
    private ArrayList <Reuniao> reunioes;
    
    public Pessoa(String nome) {
        this.nome = nome;
    }

	public String getNome() { return this.nome;}
	public void setNome(String nome) { this.nome = nome; }
	public ArrayList<Reuniao> getReunioes() { return this.reunioes; }
	public void setReunioes(ArrayList<Reuniao> reunioes) { this.reunioes = reunioes; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pessoa [nome=").append(nome).append(", reunioes=");
        if (reunioes.isEmpty()) {
            sb.append("Nenhuma reunião");
        } else {
            sb.append("\n");
            for (Reuniao reuniao : reunioes) {
                sb.append("  - ").append(reuniao.toString()).append("\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}