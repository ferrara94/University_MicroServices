package com.develop.university.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop.university.model.User;

@RestController
public class UsersController {
	
	@Value("${users.username:empty username}")
	private String username;
	
	@Value("${users.password:empty user}")
	private String password;
	
	@Value("${users.role:empty role}")
	private String role;
	
	@GetMapping(value="get/list/users")
	public ResponseEntity<?> getUsernames() {
		
		List<User> users = new LinkedList<>();
		User user;
		
		String[] usernameList = username.split(" ");
		String[] passwordList = password.split(" ");;
		String[] roleList = role.split(" ");;
		
		int i;
		String username, password, role;
		for(i=0; i<usernameList.length; i++) {
			username = usernameList[i];
			password = passwordList[i];
			role = roleList[i];
			user = new User(username, password, role);
			users.add(user);
		}

		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	

}
