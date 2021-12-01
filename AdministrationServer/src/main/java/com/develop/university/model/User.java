package com.develop.university.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{
	
	private static final long serialVersionUID = -6345133424298836388L;
	
	private String username;
	private String password;
	private String role;
	
	public User() {}

	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	
}
