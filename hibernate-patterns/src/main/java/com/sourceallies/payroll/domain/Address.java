package com.sourceallies.payroll.domain;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS")
public class Address extends AuditableEntity {
	private String streetAddress;
	private String city;
	private String state;
	private Integer zip;

    @Column(name = "STREET_ADDRESS")
    public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

    @Column(name = "CITY")
    public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

    @Column(name = "STATE")
    public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

    @Column(name = "ZIP_CODE")
    public Integer getZip() {
		return zip;
	}
	public void setZip(Integer zip) {
		this.zip = zip;
	}
}
