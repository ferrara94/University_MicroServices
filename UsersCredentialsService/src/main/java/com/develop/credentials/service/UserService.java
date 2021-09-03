package com.develop.credentials.service;

import java.util.List;

import com.develop.credentials.model.User;

public interface UserService {
	
	public List<User> getAllUsers();

	public User getUserByUserId(String UserId);
	
	public void addUser(User user);
	
	public void addUsers(List<User> users);
	
	public boolean userCredentialsJustEntered(String userid, String role);
	
	
}
