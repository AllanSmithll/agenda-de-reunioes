package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import models.Pessoa;

public class DAOPessoa  extends DAO<Pessoa>{
	
	public Pessoa read(Object chave) {
		try {
			String nome = (String) chave;
			TypedQuery<Pessoa> q = manager.createQuery("select p from Pessoa p where p.nome = :n ",Pessoa.class);
			q.setParameter("n", nome);

			return q.getSingleResult();
		}catch(NoResultException e){
			return null;}
	}
	
	public List<Pessoa> readAll(){
		TypedQuery<Pessoa> q = manager.createQuery("select p from Pessoa p LEFT JOIN FETCH p.reunioes ", Pessoa.class);
		return  q.getResultList();
	}
	//listarPessoasEmMaisDeNReunioes
	public List<Pessoa> listarPessoasEmMaisDeNReunioes(int quantidade){
		TypedQuery<Pessoa> q = manager.createQuery("select p from Pessoa p LEFT JOIN FETCH p.reunioes where size(p.reunioes) > :n",Pessoa.class);
		q.setParameter("n", quantidade);
		return q.getResultList();
	}
	

}
