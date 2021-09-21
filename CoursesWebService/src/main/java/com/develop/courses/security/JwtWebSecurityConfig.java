package com.develop.courses.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoderBean() 
	{
		return new BCryptPasswordEncoder();
	}
	
	//private static final String[] NOAUTH_MATCHER = {""}; -> could produce Error creating bean with name 'springSecurityFilterChain' because of ""
	private static final String[] NOAUTH_MATCHER = {"/api/courses/noAuth/**"};
		
	private static final String[] USER_MATCHER = { "/api/courses/get/**"};
	private static final String[] ADMIN_MATCHER = { "/api/courses/post/**"};
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception 
	{
		
	}
	
	@Override
	public void configure(WebSecurity webSecurity) 
	{
		
	}
	
	
}
