/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daodb4o;

import java.util.List;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Carro;

public class DAOCarro extends DAO<Carro>{

	public Carro read (Object chave){
		String placa = (String) chave;	//casting para o tipo da chave
		Query q = manager.query();
		q.constrain(Carro.class);
		q.descend("placa").constrain(placa);
		List<Carro> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	//--------------------------------------------
	//  consultas de Carro
	//--------------------------------------------
	public List<Carro> carrosNAlugueis(int n){
		Query q = manager.query();
		q.constrain(Carro.class);
		q.constrain(new Filtro(n));
		return q.execute();
	}

	
	//classe interna
	class Filtro implements Evaluation {
		private int n;
		public Filtro(int n) {
			this.n = n;
		}
		public void evaluate(Candidate candidate) {
			Carro car = (Carro) candidate.getObject();
			if(car.getAlugueis().size()== n) 
				candidate.include(true); 
			else		
				candidate.include(false);
		}
	}


}
