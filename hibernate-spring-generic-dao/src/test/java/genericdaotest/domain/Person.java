package genericdaotest.domain;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Integer weight;

	public Person(String name, Integer weight) {
		this.name = name;
		this.weight = weight;
	}

	// Default constructor needed by Hibernate
	protected Person() {

	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}
