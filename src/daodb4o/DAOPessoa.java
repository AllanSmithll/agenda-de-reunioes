/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * DAO Pessoa
 * @author Allan Amancio, Marcio Jose
 **********************************/

package daodb4o;

import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import models.Pessoa;

public class DAOPessoa extends DAO<Pessoa>{

	public Pessoa read (Object chave){
		String nome = (String) chave;
		Query q = manager.query();
		q.constrain(Pessoa.class);
		q.descend("nome").constrain(nome);
		List<Pessoa> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public List<Pessoa> listarPessoasEmMaisDeNReunioes(int quantidade) {
	    List<Pessoa> pessoasComMaisDeNReunioes = new ArrayList<>();

	    Query q = manager.query();
	    q.constrain(Pessoa.class);
	    List<Pessoa> resultadoConsulta = q.execute();
	    for (Pessoa pessoa : resultadoConsulta) {
	        int numeroReunioes = pessoa.getReunioes().size();
	        if (numeroReunioes > quantidade) {
	            pessoasComMaisDeNReunioes.add(pessoa);
	        }
	    }
	    return pessoasComMaisDeNReunioes;
	}
}

