package org.landal.hibernate.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "USERS")
@NamedQueries({ @NamedQuery(name = User.FIND_BY_NAME, query = "select u from User u where u.name = :name") })
public class User {
	
	public static final String FIND_BY_NAME = "User.findByName";
	
	public static User newInstance(String name){
		
		if(name == null){
			throw new NullPointerException();
		}
		
		User user = new User();		
		user.setName(name);
		return user;
	}

	@Id
	@GeneratedValue
	private Long id;

	@Version
	@Column(name = "VERSION")
	private Integer version;

	@Column(length = 32)
	private String name;

	@Column(length = 50)
	private String surname;

	@ManyToMany
	private Set<Role> roles = new HashSet<Role>();

	public User() {
	}

	public boolean addRole(Role role) {
		return roles.add(role);
	}

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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Set<Role> getRoles() {
		return Collections.unmodifiableSet(roles);
	}

}
