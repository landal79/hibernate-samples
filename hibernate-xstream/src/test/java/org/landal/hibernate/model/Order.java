package org.landal.hibernate.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ORDERS")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "SSN")
	private String SSN;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="ORDER_ORDER_ITEMS", joinColumns={
	    @JoinColumn(name="ORDER_ID", referencedColumnName="ID")
	}, inverseJoinColumns={
	    @JoinColumn(name="ORDER_ITEM_ID", referencedColumnName="ID")
	})
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	public Order() {
	
	}
	
	public void addOrderItem(OrderItem orderItem){
		
		if(orderItem == null){
			throw new NullPointerException();
		}
		
		orderItems.add(orderItem);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<OrderItem> getOrderItems() {
		return Collections.unmodifiableList(orderItems);
	}
	

}
