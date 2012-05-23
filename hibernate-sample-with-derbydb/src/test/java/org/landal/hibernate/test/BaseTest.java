package org.landal.hibernate.test;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.naming.java.javaURLContextFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class BaseTest {

	public static final String JAVA_JDBC_DS = "java:/jdbc/tutorialDS";

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				javaURLContextFactory.class.getName());
		System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
	
		InitialContext ic = new InitialContext();
		ic.createSubcontext("java:");		
		ic.createSubcontext("java:/jdbc");

		EmbeddedDataSource ds = new EmbeddedDataSource();
		ds.setDatabaseName("tutorialDB");
		// tell Derby to create the database if it does not already exist
		ds.setCreateDatabase("create");

		ic.bind(JAVA_JDBC_DS, ds);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {

		InitialContext ic = new InitialContext();
		ic.unbind(JAVA_JDBC_DS);
		
	}
}
