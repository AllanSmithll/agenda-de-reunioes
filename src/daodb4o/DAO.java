/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Persistencia de Objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

package daodb4o;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;


public abstract class DAO<T> implements DAOInterface<T> {
	protected static ObjectContainer manager;

	public static void open(){	
		manager = Util.conectarBanco();				//banco local
		//manager = Util.conectarBancoRemoto();		//banco remoto
	}

	public static void close(){
		Util.desconectar();
	}

	//----------CRUD-----------------------

	public void create(T obj){
		manager.store( obj );
	}

	public abstract T read(Object chave);	//sobrescrito nas subclasses

	public T update(T obj){
		manager.store(obj);
		return obj;
	}

	public void delete(T obj) {
		manager.delete(obj);
	}

	public void refresh(T obj){
		manager.ext().refresh(obj, Integer.MAX_VALUE);
	}

	@SuppressWarnings("unchecked")
	public List<T> readAll(){
		manager.ext().purge();  	//limpar cache do manager

		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		Query q = manager.query();
		q.constrain(type);
		return (List<T>) q.execute();
	}

	@SuppressWarnings("unchecked")
	//deletar todos objetos de um tipo (e subtipo)
	public void deleteAll(){
		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		Query q = manager.query();
		q.constrain(type);
		for (Object t : q.execute()) {
			manager.delete(t);
		}
	}

	//--------transação---------------
	public static void begin(){	
	}		// tem que ser vazio

	public static void commit(){
		manager.commit();
	}
	public static void rollback(){
		manager.rollback();
	}

	//	gerar novo id para uma classe T
	//  consulta o maior valor armazenado no atributo "id" 

	public int gerarId() {
		@SuppressWarnings("unchecked")
		Class<T> type =(Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		//verificar se o banco esta vazio 
		if(manager.query(type).size()==0) {
			return 1;			//primeiro id gerado
		}
		else {
			//obter o maior valor de id para o tipo
			Query q = manager.query();
			q.constrain(type);
			q.descend("id").orderDescending();
			List<T> resultados =  q.execute();
			if(resultados.isEmpty()) 
				return 1; 		//nenhum resultado, retorna primeiro id 
			else 
				try {
					//obter objeto localizado
					T objeto =  resultados.get(0);
					Field atributo = type.getDeclaredField("id") ;
					atributo.setAccessible(true);
					//obter atributo id do objeto localizado e incrementa-lo
					int maxid = (Integer) atributo.get(objeto);  //valor do id
					return maxid+1;

				} catch(NoSuchFieldException e) {
					throw new RuntimeException("classe "+type+" - nao tem atributo id");
				} catch (IllegalAccessException e) {
					throw new RuntimeException("classe "+type+" - atributo id inacessivel");
				}
		}
	}

}

