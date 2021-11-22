package com.develop.webapp.security;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("customdetailsservice")
public class CustomUserDetailsService {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserParams params;
	
	public UserEntity getUser(String userId){
		
		LOG.info(String.format(" --- Start retriving user %s from external service ---", userId));
		System.err.println("MANUAL LOG: Start retriving user " +userId + " from external service  " + params.getServiceURL());
		
		RestTemplate restTemplate = new RestTemplate();
		
		URI url = null;
		try {
			url = new URI(params.getServiceURL() + userId);
		} catch (URISyntaxException e) {
			LOG.warn(String.format(" --- Retriving error from external service %s---", params.getServiceURL()));
			e.printStackTrace();
		}
		
		LOG.info(String.format(" --- End retring user %s from external service ---", userId));
		System.err.println("MANUAL LOG: End retriving user " +userId + " from external service " + params.getServiceURL());
		
		UserEntity ue = null;
		
		try {
			ue = restTemplate.getForObject(url, UserEntity.class);
		}
		catch (Exception e) {
			LOG.warn(" --- ERROR REST TEMPALE CALL: CONNECTION REFUSED PROBABLY CAUSED BY THE INACTIVITY OF USERS CREDENTIALS SERVICE --- --- ");
			System.err.println("--- ERROR REST TEMPALE CALL: CONNECTION REFUSED PROBABLY CAUSED BY THE INACTIVITY OF USERS CREDENTIALS SERVICE ---");
			System.err.println(e);
		}
		
		return ue;
	}
	
	
	
		
	

}
