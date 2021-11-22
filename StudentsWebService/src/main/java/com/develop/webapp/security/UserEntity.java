package com.develop.webapp.security;

import lombok.Data;

@Data
public class UserEntity {

	private String userid;
	
	private String password;
	
	private boolean active;
	
	private String role;

	public UserEntity(String userid, String password, boolean active, String role) {
		super();
		this.userid = userid;
		this.password = password;
		this.active = active;
		this.role = role;
	}
	
    public UserEntity() {}
	
}
