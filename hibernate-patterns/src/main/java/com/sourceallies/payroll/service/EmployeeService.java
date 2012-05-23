package com.sourceallies.payroll.service;

import java.util.Date;
import java.util.List;

import com.sourceallies.payroll.dao.HibernateEmployeeDao;
import com.sourceallies.payroll.domain.Address;
import com.sourceallies.payroll.domain.Employee;
import com.sourceallies.payroll.domain.PayCheck;
import com.sourceallies.payroll.domain.PayRate;

public class EmployeeService {

    HibernateEmployeeDao dao;
	
	public Employee createNewEmployee(String firstName, String lastName, String ssn, PayRate payRate, Address address){
		Employee employee = dao.getNewInstance();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setSsn(ssn);
		
		employee.setAddress(address);
		employee.setPay(payRate);
        payRate.setAssociatedEmployee(employee);

        dao.save(employee);
		
		return employee;
	}
		
	public PayCheck createPaycheck(String ssn, Date beginPayDate, Date endPayDate, double hoursWorked){
        Employee example = dao.getNewInstance();
        example.setSsn(ssn);
        List<Employee> results = dao.getByExample(example);

        Employee employee = results.get(0);
		PayCheck paycheck = employee.createPaycheck(beginPayDate, endPayDate, hoursWorked);
		
		dao.update(employee);
		
		return paycheck;
	}
	
	public void setDao(HibernateEmployeeDao dao) {
        this.dao = dao;
    }
}
