package com.develop.credentials.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.develop.credentials.security.CustomUserDetailsService;
import com.develop.credentials.security.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@RestController
public class JwtAuthController {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthController.class);

	@Value("${security.header}")
	private String tokenHeader;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
		
	@Autowired
	@Qualifier("customUserDetailsService")
	CustomUserDetailsService userDetailsService;
	
	@PostMapping(value = "${security.uri}")
	public ResponseEntity<?> createAuthToken(@RequestBody JwtTokenRequest authRequest) {
		
		logger.info("--- Start: Authentication and Token Generation ---");
		System.err.println("--- MANUAL LOG: Start Authentication and Token Generation ---");

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authRequest.getUsername());
		
		
		if (userDetails == null) {
					
					ObjectMapper mapper = new ObjectMapper();
					ObjectNode responseNode = mapper.createObjectNode();
					
								
					responseNode.put("code", HttpStatus.BAD_REQUEST.toString());
					responseNode.put("message", "Credentials not valid!");
				
					return new ResponseEntity<>(responseNode, HttpStatus.BAD_REQUEST);
		}
		
		if(!userDetails.getPassword().equals(authRequest.getPassword())) {

			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			
						
			responseNode.put("code", HttpStatus.BAD_REQUEST.toString());
			responseNode.put("message", "Credentials not valid!");
		
			logger.warn("--- Credentials not valid! Check password ---");
			
			
			return new ResponseEntity<>(responseNode, HttpStatus.BAD_REQUEST);
		}
				
		
		System.err.println("pass: "+userDetails.getPassword());
		
		logger.info(" --- JWT TOKEN UTIL GENERATE  ---");
		System.err.println(" --- JWT TOKEN UTIL GENERATE  ---");
		
		final String token = jwtTokenUtil.generateToken(userDetails);
				
		logger.warn(String.format("Token: %s", token));
			
		logger.info("--- END: Authentication and Token Generation ---");
		
		return ResponseEntity.ok(new JwtTokenResponse(token));


	}
	
	
	@GetMapping(value = "testAuth")
	public ResponseEntity<?> testAuthToken() {
		
	
		logger.warn("--- TEST AUTH TOKEN ---");
		System.err.println(" --- TEST AUTH TOKEN  ---");
		
		return new ResponseEntity<>("test ok",HttpStatus.OK);
	}
	
	
	
	
}
