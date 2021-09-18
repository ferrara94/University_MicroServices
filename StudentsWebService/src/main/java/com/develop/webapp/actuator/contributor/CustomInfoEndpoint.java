package com.develop.webapp.actuator.contributor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import com.develop.webapp.repository.StudentRepository;

import lombok.Data;

@Component
@Endpoint(id = "customStudentsInfo")
@Data
public class CustomInfoEndpoint {
	
	@Autowired
	StudentRepository repo;

	@ReadOperation
	public Map<String, Object> customStudentsInfo() {
		
		Long studentsNumber = repo.countStudents();
		
		Map<String, Object> info = new HashMap<>();
		
		info.put("Custom - students number in DB", studentsNumber);
		
		return info;
		
	}
	
	@ReadOperation
	public String customEndpointByString(@Selector String name) {
		return String.format("Custom - students metric with @Selector String name %s", name);
	}
	
}
