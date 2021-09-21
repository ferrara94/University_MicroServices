package com.develop.courses.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("usercredential") //properties from application.yml
@Data
public class UserParams {
	private String serviceURL;
	private String userId;
	private String password;
}
