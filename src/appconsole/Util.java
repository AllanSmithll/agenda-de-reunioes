


package appconsole;

import java.util.List;

import javax.management.Query;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import models.Aluguel;
import models.Reuniao;



public class Util {
	private static ObjectContainer manager;
	
	public static ObjectContainer conectarBanco(){
		if (manager != null)
			return manager;		//ja tem uma conexao
		
		//---------------------------------------------------------------
		//configurar, criar e conectar banco local (na pasta do projeto
		//---------------------------------------------------------------
		
		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0);  // mensagens na tela 0(desliga),1,2,3...
		
		// habilitar cascata na altera��o, remo��o e leitura
		config.common().objectClass(Reuniao.class).cascadeOnDelete(false);;
		config.common().objectClass(Reuniao.class).cascadeOnUpdate(true);;
		config.common().objectClass(Reuniao.class).cascadeOnActivate(true);
		config.common().objectClass(Pessoa.class).cascadeOnDelete(false);;
		config.common().objectClass(Pessoa.class).cascadeOnUpdate(true);;
		config.common().objectClass(Pessoa.class).cascadeOnActivate(true);
		
		//conexao local
		manager = Db4oEmbedded.openFile(config, "banco.db4o");
		return manager;
	}
	
	public static void desconectar() {
		if(manager!=null) {
			manager.close();
			manager=null;
		}
	}
	
	public static int gerarIReuniao() {
		if(manager.query(Reuniao.class).size()==0) 
			//classe nao registrada no banco
			return 1;
		
		Query q = manager.query();
		q.constrain(Reuniao.class);
		q.descend("id").orderDescending();
		List<Reuniao> resultados = q.execute();

		if(resultados.size()>0) {
			Aluguel aluguel = resultados.get(0);    //max
			return aluguel.getId() + 1;
		}
		else
			return 1; 	//nenhum objeto aluguel encontrado
	}
	

}
