package com.develop.credentials.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);
		
	@Value("${security.uri}")
	private String authenticationPath;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception 
	{
		LOG.info(" --- CONFIGURE GLOBAL ---");
		System.err.println(" --- CONFIGURE GLOBAL ---");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderBean());
	}

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { // TODO Auto-generated method stub super.configure(auth); }
	 */
	
	@Bean
	public PasswordEncoder passwordEncoderBean() 
	{
		return new BCryptPasswordEncoder();
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests().anyRequest().authenticated();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, authenticationPath)
		.antMatchers(HttpMethod.OPTIONS, "/**")
		.and().ignoring()
		.antMatchers(HttpMethod.GET);
	}
	
	
	
}
