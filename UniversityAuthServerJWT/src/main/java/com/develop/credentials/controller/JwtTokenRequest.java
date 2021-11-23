package com.develop.credentials.controller;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtTokenRequest implements Serializable{

	private static final long serialVersionUID = 7491938729931516918L;

	private String username;
	private String password;
	
	public JwtTokenRequest() {}

	public JwtTokenRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	
	
}
