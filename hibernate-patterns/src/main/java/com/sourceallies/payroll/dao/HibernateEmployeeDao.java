package com.sourceallies.payroll.dao;

import com.sourceallies.payroll.domain.Employee;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class HibernateEmployeeDao extends BaseDaoHibernate<Employee> {

    public HibernateEmployeeDao() {
        setQueryClass(Employee.class);
    }

    @Override
    protected Criteria getExampleCriteria(Employee example) {
        Criteria crit = super.getExampleCriteria(example);

        if(example.getAddress() != null) {
            crit.createCriteria("address").add(Example.create(example.getAddress()));
        }

        return crit;
    }

    @SuppressWarnings("unchecked")
    public List<Employee> getEmployeesByHoursWorkedJoin(double hours) {
        Criteria crit = getBaseCriteria();
        crit.createCriteria("payChecks").add(Restrictions.eq("hoursWorked", hours));
        crit.setFetchMode("address", FetchMode.JOIN);

        return crit.list();
    }

    @SuppressWarnings("unchecked")
    public List<Employee> getEmployeesByHoursWorkedSelect(double hours) {
        Criteria crit = getBaseCriteria();
        crit.createCriteria("payChecks").add(Restrictions.eq("hoursWorked", hours));
        crit.setFetchMode("address", FetchMode.SELECT);

        return crit.list();
    }

    @SuppressWarnings("unchecked")
    public List<Employee> getEmployeesByHoursWorkedDefault(double hours) {
        Criteria crit = getBaseCriteria();
        crit.createCriteria("payChecks").add(Restrictions.eq("hoursWorked", hours));
        
        return crit.list();
    }
}
