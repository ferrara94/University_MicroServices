package com.develop.courses.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	@Qualifier("customUserDetailsServiceCourse")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenAuthFilter jwtTokenAuthFilter;
	
	@Value("${security.uri}")
	private String authenticationPath;
	
	@Bean
	public PasswordEncoder passwordEncoderBean() 
	{
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderBean());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception 
	{
		return super.authenticationManagerBean();
	}
	
	//private static final String[] NOAUTH_MATCHER = {""}; -> could produce Error creating bean with name 'springSecurityFilterChain' because of ""
	private static final String[] NOAUTH_MATCHER = {"/api/courses/noAuth/**"};
		
	private static final String[] USER_MATCHER = { "/api/courses/get/**"};
	private static final String[] ADMIN_MATCHER = { "/api/courses/post/**"};
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception 
	{
		httpSecurity.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers(NOAUTH_MATCHER).permitAll() 
		.antMatchers(USER_MATCHER).hasAnyRole("USER")
		.antMatchers(ADMIN_MATCHER).hasAnyRole("ADMIN")
		.anyRequest().authenticated();
		
		httpSecurity.addFilterBefore(jwtTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
		
		httpSecurity.headers().frameOptions()
		.sameOrigin().cacheControl();  
	}
	
	@Override
	public void configure(WebSecurity webSecurity) 
	{
		webSecurity.ignoring()
		.antMatchers(HttpMethod.POST, authenticationPath)
		.antMatchers(HttpMethod.OPTIONS, "/**")
		.and().ignoring()
		.antMatchers(HttpMethod.GET, "/");
	}
	
	
}
