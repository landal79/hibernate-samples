package org.landal.hibernate.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.landal.hibernate.test.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTest extends BaseTest {
	
	private static Logger logger = LoggerFactory.getLogger(UserTest.class);

	@Test
	public void testNewUser() {

		EntityManager entityManager = getEntityManagerInstance();
		entityManager.getTransaction().begin();

		User user = User.newInstance(Long.toString(new Date().getTime()));

		entityManager.persist(user);
		entityManager.getTransaction().commit();

		// see that the ID of the user was set by Hibernate
		logger.info("user=" + user);

		User foundUser = entityManager.find(User.class, user.getId());

		// note that foundUser is the same instance as user and is a concrete
		// class (not a proxy)
		logger.info("foundUser=" + foundUser);

		assertEquals(user.getName(), foundUser.getName());

		entityManager.close();
	}

	@Test
	public void testNewUserAndAddRole() {

		EntityManager entityManager = getEntityManagerInstance();
		entityManager.getTransaction().begin();

		User user = User.newInstance(Long.toString(new Date().getTime()));
		Role role = Role.newInstance(Long.toString(new Date().getTime()));

		entityManager.persist(user);
		entityManager.persist(role);
		entityManager.getTransaction().commit();

		assertEquals(0, user.getRoles().size());

		entityManager.getTransaction().begin();

		user.addRole(role);

		entityManager.merge(user);

		entityManager.getTransaction().commit();

		assertEquals(1, user.getRoles().size());

		entityManager.close();
	}

	@Test
	public void testFindUser() throws Exception {

		EntityManager entityManager = getEntityManagerInstance();

		entityManager.getTransaction().begin();

		Role role = Role.newInstance(Long.toString(new Date().getTime()));
		User user = User.newInstance(Long.toString(new Date().getTime()));		
		user.addRole(role);

		entityManager.persist(role);
		entityManager.persist(user);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManager = getEntityManagerInstance();

		User foundUser = entityManager
				.createNamedQuery(User.FIND_BY_NAME, User.class)
				.setParameter("name", user.getName()).getSingleResult();

		logger.info(foundUser.toString());

		assertEquals(user.getName(), foundUser.getName());

		assertEquals(1, foundUser.getRoles().size());

		logger.info(foundUser.getRoles().getClass().toString());

		entityManager.close();
	}

	@Test(expected = LazyInitializationException.class)
	public void testFindUser_throwsLazyException() throws Exception {

		EntityManager entityManager = getEntityManagerInstance();
		entityManager.getTransaction().begin();

		Role role = Role.newInstance(Long.toString(new Date().getTime()));
		User user = User.newInstance(Long.toString(new Date().getTime()));
		user.addRole(role);

		entityManager.persist(role);
		entityManager.persist(user);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManager = getEntityManagerInstance();

		User foundUser = entityManager
				.createNamedQuery(User.FIND_BY_NAME, User.class)
				.setParameter("name", user.getName()).getSingleResult();

		entityManager.close();

		//try to get roles with closed session
		assertEquals(1, foundUser.getRoles().size());
	}

	private EntityManager getEntityManagerInstance() {
		EntityManager entityManager = Persistence.createEntityManagerFactory(
				PERSISTENCE_UNIT).createEntityManager();
		return entityManager;
	}
}
