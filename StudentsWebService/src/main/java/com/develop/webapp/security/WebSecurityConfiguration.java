package com.develop.webapp.security;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	CustomUserDetailsService userService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	};
	
	private static final String[] USER_MATCHER = { "/api/students/get/**"};
	private static final String[] ADMIN_MATCHER = { "/api/students/post/**"};
	
	private static final String[] SWAGGER_MATCHER = {
														"/v2/api-docs",
												        "/configuration/ui",
												        "/swagger-resources/**",
												        "/configuration/security",
												        "/swagger-ui.html",
												        "/webjars/**"
											       	};
	
	@Override
	protected void configure(HttpSecurity http) 
			throws Exception
	{
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(SWAGGER_MATCHER).permitAll()
		.antMatchers(USER_MATCHER).hasAnyRole("USER")
		.antMatchers(ADMIN_MATCHER).hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.httpBasic()//.realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}

	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		
		UserBuilder users = User.builder();
		
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		
		UserEntity u = userService.getUser("Felice");
		
					
			manager.createUser(
					 users
					 	.username(u.getUserid())
					 	.password(new BCryptPasswordEncoder().encode(u.getPassword()))
					 	.roles(u.getRole())
					 	.build());
				
		
		return manager;
	}

	@Override
	public void configure(WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
	
	
}