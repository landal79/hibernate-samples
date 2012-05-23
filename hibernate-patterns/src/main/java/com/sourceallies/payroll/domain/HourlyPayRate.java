package com.sourceallies.payroll.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Hourly")
public class HourlyPayRate extends PayRate {
	
	private double hourlyPayRate;
	private double overtimeRate;
		
	@Override
	protected double computePayForRange(Date beginPayDate, Date endPayDate, double hoursWorked) {
		
		Calendar beginCal = Calendar.getInstance();
		beginCal.setTime(beginPayDate);
		
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endPayDate);
		
		int beginDay = beginCal.get(Calendar.DAY_OF_YEAR);
		int endDay = endCal.get(Calendar.DAY_OF_YEAR);
		int daysWorked = endDay - beginDay;
		double regPayHours = daysWorked * 8;
		
		double totalPay = regPayHours * hourlyPayRate;
		
		if( regPayHours < hoursWorked){
			totalPay += (hoursWorked - regPayHours) * overtimeRate;
		}
		
		return totalPay;
	}


	public double getHourlyPayRate() {
		return hourlyPayRate;
	}


	public void setHourlyPayRate(double hourlyPayRate) {
		this.hourlyPayRate = hourlyPayRate;
	}


	public double getOvertimeRate() {
		return overtimeRate;
	}


	public void setOvertimeRate(double overtimeRate) {
		this.overtimeRate = overtimeRate;
	}

}
