package com.develop.credentials.controller;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtTokenResponse implements Serializable{

	private static final long serialVersionUID = 5689788949750781501L;

	private final String token;
	
}
