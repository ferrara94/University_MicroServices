package com.develop.courses.security;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("customUserDetailsServiceCourse")
public class CustomUserDetailService implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailService.class);
	
	@Autowired
	private UserParams userParams;
	
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

		LOG.info(" --- START loadUserByUsername() ---");
		
		String ErrMsg = "";
		
		if (userid == null || userid.length() < 2) 
		{
			ErrMsg = "Username (userid) not available/null or not valid";
			
			LOG.warn(ErrMsg);
		}
		
		UserEntity user = this.getUser(userid);
		
		if (user == null || userid.length() < 2) 
		{
			ErrMsg = String.format("User %s not found!!", userid);
			
			LOG.warn(ErrMsg);
			return null;
		}
		
		UserBuilder builder = null;
		
		builder = org.springframework.security.core.userdetails.User.withUsername(user.getUserid());
		builder.disabled((user.isActive() ? false : true));
		builder.password(user.getPassword());
		user.setRole("ROLE_" + user.getRole());
		
		
		String[] profiles = {user.getRole()};
				 //.stream().map(a -> "ROLE_" + a).toArray(String[]::new);
		
		builder.authorities(profiles);
		
		LOG.info(" --- END loadUserByUsername() ---");
		
		return builder.build();
		

	}
	
	public UserEntity getUser(String userId){
		
		LOG.info(String.format(" --- Start retriving user %s from external service ---", userId));
		System.err.println("MANUAL LOG: Start retriving user " +userId + " from external service  " + userParams.getServiceURL());
		
		RestTemplate restTemplate = new RestTemplate();
		
		URI url = null;
		try {
			url = new URI(userParams.getServiceURL() + userId);
		} catch (URISyntaxException e) {
			LOG.warn(String.format(" --- error URI from external service %s---", userParams.getServiceURL()));
			e.printStackTrace();
		}
		
		UserEntity ue = null;
		
		try {
			ue = restTemplate.getForObject(url, UserEntity.class);
		}
		catch (Exception e) {
			LOG.warn(" --- ERROR REST TEMPALE CALL in S.Course: CONNECTION REFUSED PROBABLY CAUSED BY THE INACTIVITY OF USERS CREDENTIALS SERVICE --- --- ");
			System.err.println("--- ERROR REST TEMPALE CALL in S.Course:: CONNECTION REFUSED PROBABLY CAUSED BY THE INACTIVITY OF USERS CREDENTIALS SERVICE ---");
			System.err.println(e.toString());
		}
		
		return ue;
	}

}
