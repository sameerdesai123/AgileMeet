package com.agilemeet.model;

public class Attender {
	private String name;
	private String email;
	private int id;
	
	
	@Override
	public String toString() {
		return "Attender [name=" + name + ", email=" + email + ", id=" + id + "]";
	}

	public Attender(String name, String email, int id) {
		super();
		this.name = name;
		this.email = email;
		this.id = id;
	}
	
	public Attender(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
