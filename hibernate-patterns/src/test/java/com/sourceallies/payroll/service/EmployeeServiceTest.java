package com.sourceallies.payroll.service;

import java.util.Calendar;
import java.util.List;

import com.sourceallies.payroll.dao.HibernateEmployeeDao;
import com.sourceallies.payroll.dao.PaginationInfo;
import com.sourceallies.payroll.domain.Address;
import com.sourceallies.payroll.domain.Employee;
import com.sourceallies.payroll.domain.HourlyPayRate;
import com.sourceallies.payroll.domain.PayCheck;
import com.sourceallies.payroll.domain.SalaryPayRate;
import com.sourceallies.payroll.util.HibernateUtility;

import junit.framework.TestCase;
import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;

public class EmployeeServiceTest extends TestCase {

    private SessionFactory sessionFactory;
    private EmployeeService service = null;
    private HibernateEmployeeDao dao = null;

    public void setUp(){
        try {
            sessionFactory = HibernateUtility.getSessionFactory();
            dao = new HibernateEmployeeDao();
            dao.setSessionFactory(sessionFactory);

            service = new EmployeeService();
            service.setDao(dao);
        }
        finally {
            sessionFactory.getCurrentSession().beginTransaction();
        }
    }

    public void tearDown() {
        try {
            dao = null;
            service = null;
        }
        finally {
            Session session = sessionFactory.getCurrentSession();
            session.getTransaction().commit();
        }
    }

    protected void flushSession() {
        sessionFactory.getCurrentSession().flush();
    }

    protected void clearSession() {
        sessionFactory.getCurrentSession().clear();
    }

    public void test1HourlyEmployeeCreation(){
		HourlyPayRate hpr = new HourlyPayRate();
		hpr.setJobRole("Programmer");
		hpr.setHourlyPayRate(50.00);
		hpr.setOvertimeRate(75.00);
				
		Employee employee = service.createNewEmployee("Sid", "Waterman", "12341234", hpr, createDefaultAddress());

        flushSession();

        assertTrue(employee.getId() > 0);
		assertTrue(employee.getPay() instanceof HourlyPayRate);
		assertNotNull(employee.getCreatedOn());
		assertEquals("Anonymous", employee.getCreatedBy());
		assertNotNull(employee.getUpdatedBy());
		assertNotNull(employee.getUpdatedOn());
	}

	public void test2SalaryEmployeeCreation(){
		
		SalaryPayRate spr = new SalaryPayRate();
		spr.setJobRole("Programmer");
		spr.setYearlyRate(50000.00);
				
		Employee employee = service.createNewEmployee("David", "Dobel", "11111111", spr, createDefaultAddress());

        flushSession();

        assertTrue(employee.getId() > 0);
		assertTrue(employee.getPay() instanceof SalaryPayRate);
		assertNotNull(employee.getCreatedOn());
		assertEquals("Anonymous", employee.getCreatedBy());
		assertNotNull(employee.getUpdatedBy());
		assertNotNull(employee.getUpdatedOn());
	}
	
	public void test3PaycheckCreation(){
		Calendar start = Calendar.getInstance();
		start.add(Calendar.DAY_OF_YEAR, -21);
		
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DAY_OF_YEAR, -7);
		
		PayCheck check = service.createPaycheck("12341234", start.getTime(), end.getTime(), 40);

        flushSession();

        assertTrue(check.getId() > 0);
		assertEquals(1, check.getAssociatedEmployee().getPayChecks().size());
		assertNotNull(check.getCreatedOn());
		assertEquals("Anonymous", check.getCreatedBy());
		
	}
	
	public void test4FetchStrategies() {
		//This example should default to lazy loading and issue a query for 
		//each employee's address
        List<Employee> results = dao.getEmployeesByHoursWorkedDefault(40);
        checkCities(results);
        clearSession();

        //This example should issue only a single query for employees and addresses.
        results = dao.getEmployeesByHoursWorkedJoin(40);
        checkCities(results);
        clearSession();

        //This example should for lazy loading and issue queries for
        //each employees address.
        results = dao.getEmployeesByHoursWorkedSelect(40);
        checkCities(results);
    }
	
	public void test5Paging() {
		PaginationInfo pageInfo = new PaginationInfo();
		pageInfo.setFirstRow(0);
		pageInfo.setMaxResults(1);
		
		List<Employee> data = dao.getPageOfDataAll(pageInfo);
		assertNotNull(data);
		assertEquals(1, data.size());
		assertEquals(new Long(1), data.get(0).getId());
		
		pageInfo.setFirstRow(1);
		data = dao.getPageOfDataAll(pageInfo);
		assertNotNull(data);
		assertEquals(1, data.size());
		assertEquals(new Long(2), data.get(0).getId());
	}

    private void checkCities(List<Employee> results) {
        for(Employee e: results) {
            assertEquals("Des Moines", e.getAddress().getCity());
        }
    }


    private Address createDefaultAddress(){
		Address address = new Address();
		address.setStreetAddress("1234 4th Street");
		address.setCity("Des Moines");
		address.setState("IA");
		address.setZip(new Integer(11111));
		return address;
	}
}
