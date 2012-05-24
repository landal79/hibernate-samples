package org.landal.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {

	private static final SessionFactory sessionFactory;

	static {
		try {

			sessionFactory = new Configuration().configure()
					.buildSessionFactory();

		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
