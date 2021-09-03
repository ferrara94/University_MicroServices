package com.develop.credentials.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.develop.credentials.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	public User findByUserid(String userId);
	
}
