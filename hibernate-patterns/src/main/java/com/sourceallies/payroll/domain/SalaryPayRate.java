package com.sourceallies.payroll.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Salary")
public class SalaryPayRate extends PayRate {

	private double yearlyRate;
	
	@Override
	protected double computePayForRange(Date beginPayDate, Date endPayDate, double hoursWorked) {
		double payPerDay = yearlyRate / 365;
		
		Calendar beginCal = Calendar.getInstance();
		beginCal.setTime(beginPayDate);
		
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endPayDate);
		
		int beginDay = beginCal.get(Calendar.DAY_OF_YEAR);
		int endDay = endCal.get(Calendar.DAY_OF_YEAR);
		
		return payPerDay * (endDay - beginDay);
	}

	public double getYearlyRate() {
		return yearlyRate;
	}

	public void setYearlyRate(double yearlyRate) {
		this.yearlyRate = yearlyRate;
	}
}
