package com.sourceallies.payroll.domain;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Table(name="EMPLOYEE")
public class Employee extends AuditableEntity {

	private String firstName;
	private String lastName;
	private String ssn;
	private Address address;
	private PayRate pay;
    private List<PayCheck> payChecks = new ArrayList<PayCheck>();

    public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    public PayRate getPay() {
		return pay;
	}
	public void setPay(PayRate pay) {
		this.pay = pay;
	}

    public PayCheck createPaycheck(Date beginPayDate, Date endPayDate, double hoursWorked) {
		PayCheck check = pay.createPaycheck(beginPayDate, endPayDate, hoursWorked);
        getPayChecks().add(check);
        check.setAssociatedEmployee(this);
        return check;
    }


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "associatedEmployee")
    @OrderBy("payPeriodBeginDate")
    public List<PayCheck> getPayChecks() {
        return payChecks;
    }

    public void setPayChecks(List<PayCheck> payChecks) {
        this.payChecks = payChecks;
    }
}
