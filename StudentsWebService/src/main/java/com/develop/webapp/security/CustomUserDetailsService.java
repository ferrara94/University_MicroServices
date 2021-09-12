package com.develop.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;

@Service("customdetailsservice")
public class CustomUserDetailsService {
	
	@Autowired
	UserParams params;
	
	public UserEntity getUser(String userId){
		
		RestTemplate restTemplate = new RestTemplate();
		
		URI url = null;
		try {
			url = new URI(params.getServiceURL() + userId);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return restTemplate.getForObject(url, UserEntity.class);
	}
	
	
	
		
	

}
