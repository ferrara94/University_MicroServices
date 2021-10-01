package com.develop.courses.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtTokenAuthFilter extends OncePerRequestFilter{

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthFilter.class);
				
	@Autowired
	@Qualifier("customUserDetailsServiceCourse")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Value("${security.header}")
	private String tokenHeader;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		logger.info(" --- START doFilterInternal ---");
		System.err.println("MANUAL LOG: --- START doFilterInternal ---");
		
		logger.debug("Authentication from '{}'", request.getRequestURL());
		
		final String requestTokenHeader = request.getHeader(this.tokenHeader);
		
		logger.warn("--- Token {requestTokenHeader}: " + requestTokenHeader + " ---");
		System.err.println("MANUAL LOG:oken {requestTokenHeader}: " + requestTokenHeader + " ---");
		
		
		String username = null;
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
			
			jwtToken = requestTokenHeader.substring(7);
			
			try 
			{
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} 
			catch (IllegalArgumentException e) 
			{
				logger.error("IMPOSSIBLE TO GET USERID", e);
				System.err.println("MANUAL LOG: ---  IMPOSSIBLE TO GET USERID ---");
				
			} 
			catch (ExpiredJwtException e) 
			{
				logger.warn("TOKEN EXPIRED", e);
				System.err.println("MANUAL LOG: --- TOKEN EXPIRED ---");
				
			}
			
		} else 
		{
			logger.warn("--- TOKEN NOT VALID ---");
			System.err.println("MANUAL LOG: ---  TOKEN NOT VALID ---");
			
		}
		
		logger.debug("--- JWT_TOKEN_USERNAME_VALUE '{}' ---", username);
		System.err.println("MANUAL LOG: ---  JWT TOKEN - USERNAME VALUE: " + username + "---");
		
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			logger.info("--- VALIDATE TOKEN ---");
			System.err.println("MANUAL LOG: ---  VALIDATE TOKEN  ---");
			
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) 
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		logger.info("--- doFilter (request, response) ---");
		System.err.println("MANUAL LOG: ---  doFilter (request, response) ---");
		
		filterChain.doFilter(request, response);
	}
		
		
	

}
