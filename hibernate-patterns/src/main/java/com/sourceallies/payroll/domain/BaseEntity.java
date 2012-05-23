package com.sourceallies.payroll.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Identifiable{
	private Long id;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
}
