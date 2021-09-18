package com.develop.webapp.actuator.contributor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;

import com.develop.webapp.repository.StudentRepository;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class StudentsInfo implements InfoContributor{

	@Autowired
	StudentRepository repo;
	
	@Override
	public void contribute(Builder builder) {
		
		int studentsNumber = repo.findAll().size();
		
		Map<String, Object> info = new HashMap<>();
		
		info.put("students number in DB", studentsNumber);
		
		builder.withDetail("students info", info);
		
	}

}
