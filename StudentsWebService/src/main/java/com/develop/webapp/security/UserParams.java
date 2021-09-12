package com.develop.webapp.security;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Component
@ConfigurationProperties("usercredential") //properties from application.yml
@Data
public class UserParams {
	private String userId;
	private String password;
}
