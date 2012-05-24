package org.landal.hibernate.xstream;

import static org.junit.Assert.*;

import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.landal.hibernate.model.Order;
import org.landal.hibernate.model.OrderItem;
import org.landal.hibernate.util.HibernateUtility;

public class XstreamHibernateSerializerTest {

	private SessionFactory sessionFactory;

	@Before
	public void setUp() {
		try {
			sessionFactory = HibernateUtility.getSessionFactory();
		} finally {
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}

	@After
	public void tearDown() {
		try {
			sessionFactory.close();
		} catch (HibernateException e) {

		}
	}

	@Test
	public void testSerializer() {

		Session session = sessionFactory.getCurrentSession();

		Order order = new Order();
		order.setDescription("customer order");
		order.setSSN(UUID.randomUUID().toString());

		OrderItem orderItem = new OrderItem();
		orderItem.setProduct("first product");
		order.addOrderItem(orderItem);

		orderItem = new OrderItem();
		orderItem.setProduct("second product");
		order.addOrderItem(orderItem);

		order = (Order) session.merge(order);
		session.getTransaction().commit();		

		session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		session.load(Order.class, order.getId());

		XstreamHibernateSerializer serializer = new XstreamHibernateSerializer();
		String xml = serializer.toXML(order);

		assertTrue(xml.contains("first product"));
		assertTrue(xml.contains("second product"));

	}

	protected void flushSession() {
		sessionFactory.getCurrentSession().flush();
	}

	protected void clearSession() {
		sessionFactory.getCurrentSession().clear();
	}

}
