/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * DAO Reuniao
 * @authors Allan Amancio, Marcio Jose
 **********************************/
package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import models.Reuniao;

public class DAOReuniao extends DAO<Reuniao>{

	public Reuniao read (Object chave){
		int id = (int) chave;	
		Query q = manager.query();
		q.constrain(Reuniao.class);
		q.descend("id").constrain(id);
		List<Reuniao> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	public void create(Reuniao obj){
		int novoid = super.gerarId();
		obj.setId(novoid);
		manager.store( obj );
	}
	
	public List<Reuniao> listarReunioesNaData(String data){
		Query q;
		q = manager.query();
		q.constrain(Reuniao.class);
		q.descend("data").constrain(data);
		return q.execute();
	}
	
	public List<Reuniao> listarReunioesComAPessoa(String nome){
		Query q = manager.query();
		q.constrain(Reuniao.class);
		q.descend("listaDePessoas").descend("nome").constrain(nome);
		return q.execute();
	}
}
