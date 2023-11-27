/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package daojpa;



import java.util.Properties;

import org.apache.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Util {
	private static EntityManager manager;
	private static EntityManagerFactory factory;

	// gravar mensagens no arquivo log4j.log
	private static final Logger logger = Logger.getLogger(Util.class);
	
	public static EntityManager conectarBanco() {
		if (manager == null) {
			try {
				// ler dados do arquivo dados.properties
				Properties dados = new Properties();
				logger.info("Util.conectar - lendo arquivo util.properties: ");
				dados.load(DAO.class.getResourceAsStream("/daojpa/util.properties")); // dentro de src

				String sgbd = dados.getProperty("sgbd");
				String banco = dados.getProperty("banco");
				String ip = dados.getProperty("ip1"); // ip1,ip2,ip3

				logger.info("sgbd => " + sgbd);
				logger.info("banco => " + banco);
				logger.info("ip => " + ip);

				// reconfigurar algumas propriedades do persistence.xml
				Properties configuracoes = new Properties();
				if (sgbd.equals("postgresql")) {
					logger.info("configurando postgresql");
					configuracoes.setProperty("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
					configuracoes.setProperty("jakarta.persistence.jdbc.url",
							"jdbc:postgresql://" + ip + ":5432/" + banco);
					configuracoes.setProperty("jakarta.persistence.jdbc.user", "postgres");
					configuracoes.setProperty("jakarta.persistence.jdbc.password", "Allan123@#");
//					configuracoes.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
				}
				if (sgbd.equals("mysql")) {
					logger.info("configurando mysql");
					configuracoes.setProperty("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
					configuracoes.setProperty("jakarta.persistence.jdbc.url",
							"jdbc:mysql://" + ip + ":3306/" + banco + "?createDatabaseIfNotExist=true");
					configuracoes.setProperty("jakarta.persistence.jdbc.user", "root");
					configuracoes.setProperty("jakarta.persistence.jdbc.password", "");
					//configuracoes.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
				}
				// -----------------------------------------------------------------------------------
				String unit_name = "hibernate" + "-" + sgbd;
				factory = Persistence.createEntityManagerFactory(unit_name, configuracoes);
				manager = factory.createEntityManager();

			} catch (Exception e) {
				logger.info("Util.conectar - problema de configuracao: " + e.getMessage());
				System.exit(0);
			}
		}
		return manager;
	}

	public static void fecharBanco() {
		if (manager != null && manager.isOpen()) {
			manager.close();
			factory.close();
			manager = null;
		}
	}
}
