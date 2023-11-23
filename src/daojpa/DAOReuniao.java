package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Reuniao;

public class DAOReuniao extends DAO<Reuniao>{
	public Reuniao read(Object chave) {
		try {
			int id = (int) chave;
			TypedQuery<Reuniao> q = manager.createQuery("select r from Reuniao p where r.id = :n ",Reuniao.class);
			q.setParameter("n", id);
			return q.getSingleResult();
			
		}catch(NoResultException e){
			return null;}
	}
	public List<Reuniao> readAll(){
		TypedQuery<Reuniao> q = manager.createQuery("select r from Reuniao r ", Reuniao.class);
		return  q.getResultList();
	}
	
	
	public List<Reuniao> listarReunioesNaData(String data){
		TypedQuery<Reuniao> q = manager.createQuery("select r from Reuniao r where r.data = :x ", Reuniao.class);
		q.setParameter("x",data);
		return  q.getResultList();
	}
	
	public List<Reuniao> listarReunioesComAPessoa(String nome){
		TypedQuery<Reuniao> q = manager.createQuery("select r from Reuniao r LEFT JOIN FETCH r.listaDePessoas p where p.nome = :x ", Reuniao.class);
		q.setParameter("x",nome);
		return  q.getResultList();
	}
	
	
}
