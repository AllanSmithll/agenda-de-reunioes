/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * DAO Pessoa
 * @author Allan Amancio, Marcio Jose
 **********************************/

package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import models.Pessoa;
import models.Reuniao;

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
	
	// Vai pra tela como "Pessoas que estao em mais de N reunioes"
	public List<Pessoa> listarPessoasEmMaisDeNReunioes(int quantidade){
		Query q;
		q = manager.query();
		q.constrain(Pessoa.class);
		q.descend("listaDeReunioes").constrain(quantidade).greater();
		return q.execute();
	}
}

