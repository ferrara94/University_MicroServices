package com.develop.courses.security;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserEntity implements Serializable{

	private static final long serialVersionUID = 6550190627640739330L;

	private String userid;
	
	private String password;
	
	private boolean active;
	
	private String role;

	public UserEntity(String userid, String password, boolean active, String role) {
		this.userid = userid;
		this.password = password;
		this.active = active;
		this.role = role;
	}
	
	public UserEntity() {}
	
	
	
}