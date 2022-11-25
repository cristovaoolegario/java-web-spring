package com.cristovaoolegario.userdept.entities;

public class Department {
  private Long id;
	private String name;
	
	public Department() {
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
