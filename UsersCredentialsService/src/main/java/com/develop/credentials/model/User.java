package com.develop.credentials.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User implements Serializable {
	
	private static final long serialVersionUID = 4424250987630064864L;

	@Id	
	private String userid;
	
	private String password;
	
	private boolean active;
	
	@Column(unique = true)
	private String role;

}
