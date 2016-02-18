package edu.illinois.ugl.minrva.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement	
public class User {	
	private int id;
	private String username;
	private String password;

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public User() {	} // Java Bean Requirement

	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
}
