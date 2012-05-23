package com.sourceallies.payroll.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="employeeType", discriminatorType=DiscriminatorType.STRING)
public abstract class PayRate extends BaseEntity {	
	private String jobRole;
	private Employee associatedEmployee;
	
	protected abstract double computePayForRange(Date beginPayDate, Date endPayDate, double hoursWorked);
	
	public PayCheck createPaycheck(Date beginPayDate, Date endPayDate, double hoursWorked){
		
		double payAmount = computePayForRange(beginPayDate, endPayDate, hoursWorked);
		PayCheck check = new PayCheck();
		check.setAmountPaid(payAmount);
		check.setDatePaid(new Date());
		check.setPayPeriodBeginDate(beginPayDate);
		check.setPayPeriodEndDate(endPayDate);
		check.setHoursWorked(hoursWorked);
		check.setAssociatedPayRate(this);
		
		return check;
		
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "pay")
    public Employee getAssociatedEmployee() {
		return associatedEmployee;
	}

	public void setAssociatedEmployee(Employee associatedEmployee) {
		this.associatedEmployee = associatedEmployee;
	}
}
