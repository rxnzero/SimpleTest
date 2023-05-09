package com.dhlee.copy;

public class User implements Cloneable {

	public User() {
		// TODO Auto-generated constructor stub
	}

	String name;
	int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + "]";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		User cloned;
		cloned = (User)super.clone();
		return cloned;
	}
}
