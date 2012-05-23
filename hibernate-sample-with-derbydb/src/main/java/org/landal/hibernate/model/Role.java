package org.landal.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "ROLES")
public class Role {
	
	public static Role newInstance(String name){
	
		if(name == null){
			throw new NullPointerException();
		}
		
		Role role = new Role();
		role.setName(name);
		return role;
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;
	
	@Column(length = 32)
	private String name;
	
	public Role() { }
	
	@Override
	public String toString() {		
		return ToStringBuilder.reflectionToString(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
