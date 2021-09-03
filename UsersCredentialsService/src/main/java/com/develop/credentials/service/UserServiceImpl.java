package com.develop.credentials.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.develop.credentials.model.User;
import com.develop.credentials.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository repo;

	@Override
	public List<User> getAllUsers() {
		return repo.findAll();
	}

	@Override
	public User getUserByUserId(String UserId) {
		
		Optional<User> user = repo.findById(UserId);

        if(user.isPresent()) return user.get();
        else return null;
		
		
	}

	@Override
	public void addUser(User user) {
		repo.save(user);
		
	}
	
	@Override
	public boolean userCredentialsJustEntered(String userid, String role) {
		
		User u = this.getUserByUserId(userid); 
		
		if(u == null) return false;
		
				
		if( u.getUserid().equals(userid) && u.getRole().equals(role) )
			return true;
		
		return false;
	}

	@Override
	public void addUsers(List<User> users) {
		repo.saveAll(users);
	}

}
