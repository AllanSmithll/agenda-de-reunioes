/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package daodb4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import models.Pessoa;
import models.Reuniao;
public class Util {
	private static ObjectContainer manager=null;

	
	public static ObjectContainer conectarBanco(){
		if (manager != null)
			return manager;

		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0);
		
		config.common().objectClass(Pessoa.class).cascadeOnDelete(false);
		config.common().objectClass(Pessoa.class).cascadeOnUpdate(true);
		config.common().objectClass(Pessoa.class).cascadeOnActivate(true);
		config.common().objectClass(Reuniao.class).cascadeOnDelete(false);
		config.common().objectClass(Reuniao.class).cascadeOnUpdate(true);
		config.common().objectClass(Reuniao.class).cascadeOnActivate(true);
		
		manager = Db4oEmbedded.openFile(config, "banco.db4o");
		return manager;
	}
	
	public static ObjectContainer conectarBancoRemoto(){		
		ClientConfiguration config = Db4oClientServer.newClientConfiguration( ) ;
		config.common().messageLevel(0);

		config.common().objectClass(Pessoa.class).cascadeOnDelete(false);
		config.common().objectClass(Pessoa.class).cascadeOnUpdate(true);
		config.common().objectClass(Pessoa.class).cascadeOnActivate(true);
		config.common().objectClass(Reuniao.class).cascadeOnDelete(false);
		config.common().objectClass(Reuniao.class).cascadeOnUpdate(true);
		config.common().objectClass(Reuniao.class).cascadeOnActivate(true);

		String ipservidor="";
		//ipservidor = "10.0.4.43";		// computador do professor (lab3)
		ipservidor = "54.163.92.174";		// AWS
		manager = Db4oClientServer.openClient(config, ipservidor, 34000,"usuario1","senha1");
		return manager;
	}

	public static void desconectar() {
		if(manager!=null) {
			manager.close();
			manager=null;
		}
	}
}
