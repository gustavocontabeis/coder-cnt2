package br.com.cnt.model.dao.balanco;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
	//static Logger logger = Logger.getLogger(HibernateUtility.class);
	private static SessionFactory factory;
	
	static {
		try {
			factory = new Configuration()
			.configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			factory = null;
		}
	}

	public static SessionFactory getSessionFactory() {
		return factory;
	}
	
	public static Session getSession() {
		return factory.openSession();
	}
	
}
