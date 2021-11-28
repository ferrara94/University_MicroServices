package com.develop.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Value("${user:default message}")
	private String info;
	
	@GetMapping("/info")
	public String getInfo() {
		return info;
	}
	
}
