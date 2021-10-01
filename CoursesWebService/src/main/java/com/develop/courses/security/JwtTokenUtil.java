package com.develop.courses.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.develop.courses.security.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

@Component
public class JwtTokenUtil implements Serializable{

	private static final long serialVersionUID = 8679616532884477517L;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_CREATED = "iat";
	
	private Clock clock = DefaultClock.INSTANCE;
	
	@Autowired
	private JwtConfig jwtConfig;
	
	/* -------------------------------------------------------- */
	
	public String getUsernameFromToken(String token) 
	{
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getIssuedAtDateFromToken(String token) 
	{
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) 
	{
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	/* -------------------------------------------------------- */
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) 
	{
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) 
	{
		return Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(token).getBody();
	}
	
	/* -------------------------------------------------------- */
	
	private Boolean isTokenExpired(String token) 
	{
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(clock.now());
	}

	private Boolean ignoreTokenExpiration(String token) 
	{
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails) 
	{
		Map<String, Object> claims = new HashMap<>();
		
		
		return doGenerateToken(claims, userDetails);
	}
	
	private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) 
	{
		logger.info(" --- START: DO GENERATE TOKEN METHOD  ---");
		System.err.println(" --- START: DO GENERATE TOKEN METHOD  ---");
				
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.claim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
				.compact();
		
		
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) 
	{
		//JwtUserDetails user = (JwtUserDetails) userDetails;
		
		final String username = getUsernameFromToken(token);
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	
	private Date calculateExpirationDate(Date createdDate) 
	{
		return new Date(createdDate.getTime() + jwtConfig.getExpiration() * 1000);
	}
	

}
