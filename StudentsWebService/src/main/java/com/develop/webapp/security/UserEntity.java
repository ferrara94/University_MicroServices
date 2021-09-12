package com.develop.webapp.security;

import lombok.Data;

@Data
public class UserEntity {

	private String userid;
	
	private String password;
	
	private boolean active;
	
	private String role;
	
}
