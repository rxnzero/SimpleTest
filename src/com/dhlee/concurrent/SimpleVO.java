package com.dhlee.concurrent;

public class SimpleVO {
	private String id;
	private String name;
	
	public SimpleVO() {
		// TODO Auto-generated constructor stub
	}

	public SimpleVO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SimpleVO [id=" + id + ", name=" + name + "]";
	}
	
	

}
