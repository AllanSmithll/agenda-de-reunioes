
/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/


package daojpa;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;


public abstract class DAO<T> implements DAOInterface<T> {
	protected static EntityManager manager;

	public DAO(){}

	public static void open(){
		manager = Util.conectarBanco();
	}

	public static void close(){
		Util.fecharBanco();
	}
	
	public void create(T obj){
		manager.persist(obj);
	}
	public abstract T read(Object chave);

	public T update(T obj){
		return manager.merge(obj);
	}
	public void delete(T obj) {
		manager.remove(obj);
	}


	@SuppressWarnings("unchecked")
	public List<T> readAll(){
		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		TypedQuery<T> query = manager.createQuery("select x from " + type.getSimpleName() + " x",type);
		return  query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> readAllPagination(int firstResult, int maxResults) {
		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		return manager.createQuery("select x from " + type.getSimpleName() + " x",type)
				.setFirstResult(firstResult - 1)
				.setMaxResults(maxResults)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public void deleteAll(){
		Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		Query query = manager.createQuery("delete from " + type.getSimpleName());
		query.executeUpdate();

	}


	//----------------------- TRANSA��O   ----------------------
	public static void begin(){
		if(!manager.getTransaction().isActive())
			manager.getTransaction().begin();
	}
	public static void commit(){
		if(manager.getTransaction().isActive()){
			manager.getTransaction().commit();
			manager.clear();		// ---- esvazia o cache de objetos, se habilitado----
		}
	}
	public static void rollback(){
		if(manager.getTransaction().isActive())
			manager.getTransaction().rollback();
	}

	public void lock(T obj) {
		//usado somente no controle de concorrencia persimista
		manager.lock(obj, LockModeType.PESSIMISTIC_WRITE); 
	}

	// acesso direto a classe de conex�o jdbc
	public static Connection getConnectionJdbc() {
		try {
			EntityManagerFactory factory = manager.getEntityManagerFactory();
			String driver = (String) factory.getProperties().get("jakarta.persistence.jdbc.driver");
			String url = (String)	factory.getProperties().get("jakarta.persistence.jdbc.url");
			String user = (String)	factory.getProperties().get("jakarta.persistence.jdbc.user");
			String pass = (String)	factory.getProperties().get("jakarta.persistence.jdbc.password");
			Class.forName(driver);
			return DriverManager.getConnection(url, user, pass);
		} 
		catch (Exception ex) {
			return null;
		}
	}

}

