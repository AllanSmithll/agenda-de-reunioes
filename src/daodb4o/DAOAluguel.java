/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Aluguel;

public class DAOAluguel  extends DAO<Aluguel>{

	public Aluguel read (Object chave){
		int id = (int) chave;	
		Query q = manager.query();
		q.constrain(Aluguel.class);
		q.descend("id").constrain(id);
		List<Aluguel> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	//metodo da classe DAO sobrescrito DAOAluguel para
	//criar "id" sequencial 
	public void create(Aluguel obj){
		int novoid = super.gerarId();  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}

	//--------------------------------------------
	//  consultas de Aluguel
	//--------------------------------------------

	public List<Aluguel> alugueisModelo(String modelo){
		Query q;
		q = manager.query();
		q.constrain(Aluguel.class);
		q.descend("carro").descend("modelo").constrain(modelo);
		return q.execute();
	}

	public List<Aluguel> alugueisFinalizados(){
		Query q = manager.query();
		q.constrain(Aluguel.class);
		q.descend("finalizado").constrain(true);
		return q.execute();
	}
}
