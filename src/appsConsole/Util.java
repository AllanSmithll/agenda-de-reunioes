///*
// * Util class
// * @authors Allan Amancio e Marcio Jose
// */
//package appsConsole;
//
//import java.util.List;
//
////import com.db4o.Db4oEmbedded;
////import com.db4o.ObjectContainer;
////import com.db4o.config.EmbeddedConfiguration;
////import com.db4o.query.Query;
//
//import models.Pessoa;
//import models.Reuniao;
//
//public class Util {
//	private static EntityManager manager;
//	
//	public static EntityManager conectarBanco(){
//		if (manager != null)
//			return manager;		//ja tem uma conexao
//		
//		//---------------------------------------------------------------
//		//configurar, criar e conectar banco local (na pasta do projeto
//		//---------------------------------------------------------------
//		
//		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
//		config.common().messageLevel(0);
//		
//		// habilitar cascata na altera��o, remo��o e leitura
//		config.common().objectClass(Reuniao.class).cascadeOnDelete(false);
//		config.common().objectClass(Reuniao.class).cascadeOnUpdate(true);
//		config.common().objectClass(Reuniao.class).cascadeOnActivate(true);
//		config.common().objectClass(Pessoa.class).cascadeOnDelete(false);
//		config.common().objectClass(Pessoa.class).cascadeOnUpdate(true);
//		config.common().objectClass(Pessoa.class).cascadeOnActivate(true);
//		
//		//conexao local
//		manager = Db4oEmbedded.openFile(config, "banco.db4o");
//		return manager;
//	}
//	
//	public static void desconectar() {
//		if(manager!=null) {
//			manager.close();
//			manager=null;
//		}
//	}
//	
//	public static int gerarIReuniao() {
//		if(manager.query(Reuniao.class).size()==0) 
//			return 1;
//		
//		Query q = manager.query();
//		q.constrain(Reuniao.class);
//		q.descend("id").orderDescending();
//		List<Reuniao> resultados = q.execute();
//
//		if(resultados.size()>0) {
//			Reuniao reuniao = resultados.get(0);
//			return reuniao.getId() + 1;
//		}
//		else
//			return 1; 
//	}
//}
