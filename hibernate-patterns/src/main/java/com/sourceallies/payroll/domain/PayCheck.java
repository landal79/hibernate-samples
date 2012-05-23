package com.sourceallies.payroll.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class PayCheck extends AuditableEntity {
	private double amountPaid;
	private double hoursWorked;
	private Date datePaid;
	private Date payPeriodBeginDate;
	private Date payPeriodEndDate;
	private PayRate associatedPayRate;
    private Employee associatedEmployee;

    public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Date getDatePaid() {
		return datePaid;
	}
	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}
	public Date getPayPeriodBeginDate() {
		return payPeriodBeginDate;
	}
	public void setPayPeriodBeginDate(Date payPeriodBeginDate) {
		this.payPeriodBeginDate = payPeriodBeginDate;
	}
	public Date getPayPeriodEndDate() {
		return payPeriodEndDate;
	}
	public void setPayPeriodEndDate(Date payPeriodEndDate) {
		this.payPeriodEndDate = payPeriodEndDate;
	}

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    public PayRate getAssociatedPayRate() {
		return associatedPayRate;
	}
	public void setAssociatedPayRate(PayRate associatedPayRate) {
		this.associatedPayRate = associatedPayRate;
	}
	public double getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    public Employee getAssociatedEmployee() {
        return associatedEmployee;
    }
    public void setAssociatedEmployee(Employee associatedEmployee) {
        this.associatedEmployee = associatedEmployee;
    }
}
