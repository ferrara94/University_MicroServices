package com.develop.credentials.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop.credentials.model.User;
import com.develop.credentials.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserService service;
		
	@GetMapping(value = "/get/all")
	public ResponseEntity<?>  getAllUser(){
		
		LOG.info(" --- Start Get all users from DB ---");
		
		List<User> users = service.getAllUsers();
		
		LOG.info(" --- End Get all users from DB ---");
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/user/{userId}")
	public ResponseEntity<?> getUserByUserId(@PathVariable("userId") String UserId) {
		
		LOG.info(String.format(" --- Start Get user %s from DB ---", UserId));
		
		User user = service.getUserByUserId(UserId);
		
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();

		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ObjectNode responseNode = mapper.createObjectNode();
		
		if(user == null) {
			
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "user " + UserId + " not found"); 
			
			LOG.warn(String.format(" --- User %s not found ---", UserId));
						
			return new ResponseEntity<>(responseNode, headers, HttpStatus.NO_CONTENT);
		
		}
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "user " + UserId + " found"); 
		
		LOG.info(String.format(" ---User %s found ---", UserId));
				
		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
	}
	
	//-----------------------------------------
	
	@GetMapping(value = "/fetch/user/{userId}")
	public User fetchUserByUserId(@PathVariable("userId") String UserId) {
		
		LOG.info(String.format(" --- Start Get user %s from DB ---", UserId));
		
		User user = service.getUserByUserId(UserId);
				
		if(user == null) return null;
				
		
		LOG.info(String.format(" ---User %s found ---", UserId));
				
		return user;
	}
	
	@GetMapping(value = "/fetch/users")
	public List<User> fetchUsers() {
		
		LOG.info(" --- Start Get users from DB ---");
		
		List<User> users = service.getAllUsers();
				
		if(users == null) return null;
				
		
		LOG.info(" ---Users retrived ");
				
		return users;
	}
	
	// ------------------------------------
	
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<?> addNewUser(@RequestBody User user) {
		
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();

		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ObjectNode responseNode = mapper.createObjectNode();
		
		if(user == null) {
			responseNode.put("code", HttpStatus.NO_CONTENT.toString());
			responseNode.put("message", "user is null or not valid"); 
			
			return new ResponseEntity<>(responseNode, headers, HttpStatus.NO_CONTENT);
		}
		
		if(service.userCredentialsJustEntered(user.getUserid(), user.getRole())) {

			responseNode.put("code", HttpStatus.CONFLICT.toString());
			responseNode.put("message", "user /" + user.getUserid() + " and its credentials just present"); 
			
			return new ResponseEntity<>(responseNode, headers, HttpStatus.CONFLICT);
		}

		service.addUser(user);
				
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", user.getUserid() + " succesfully added"); 
		
		return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
	
	}
	
	@PostMapping(value = "/add/users")
	public ResponseEntity<?> addNewUsers(@RequestBody List<User> users) {
		
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();

		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ObjectNode responseNode = mapper.createObjectNode();
		
		if(users == null) {
			responseNode.put("code", HttpStatus.NO_CONTENT.toString());
			responseNode.put("message", "users list is null or not valid"); 
			
			return new ResponseEntity<>(responseNode, headers, HttpStatus.NO_CONTENT);
		}
		
		String message = "";
		List<User> us = new LinkedList<>();
		
		for(User user: users) {
			
			if(service.userCredentialsJustEntered(user.getUserid(), user.getRole())) {
				message += "user /" + user.getUserid() + " and its credentials just present" + " -- ";
			}else {
				us.add(user);
				message += "user /" + user.getUserid() + " successfully added" + " -- ";
			}
			
		}
		
		service.addUsers(us);
				
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", message); 
		
		return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
	
	}
	
	
	

}
