package com.develop.credentials.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("usercredential")
@Data
public class UserParams {
	
	private String serviceURL;
	private String userId;
	private String password;

}
