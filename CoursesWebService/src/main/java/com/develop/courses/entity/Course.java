package com.develop.courses.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity 
@Table(name = "COURSES")
public class Course implements Serializable{

	private static final long serialVersionUID = -2957955217188817414L;

	@Id
	@Column(name = "id_course")
	private String name;
	
	private int hours;
		
	private Long prof;
}
