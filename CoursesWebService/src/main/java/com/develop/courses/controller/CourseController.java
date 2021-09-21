package com.develop.courses.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop.courses.entity.Course;
import com.develop.courses.service.CourseService;


@RestController
@RequestMapping("/api/courses")
public class CourseController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
		
	@Autowired
	CourseService service;
	
	@GetMapping(value = "/get/all")
	public ResponseEntity<List<Course>> getCourses() {
		
		LOG.info(" --- Start Get all courses from DB ---");
		
		List<Course> courses = service.getCourses();
		
		if(courses.isEmpty()) {
			LOG.warn("--- courses list is empty ---");
			return new ResponseEntity<List<Course>>(HttpStatus.NO_CONTENT);
		}
		
		LOG.info(" --- End Get all courses from DB ---");
		
		return new ResponseEntity<List<Course>>(courses,HttpStatus.OK);
	}
	
	@PostMapping(value = "/post/course/add")
	public ResponseEntity<Course> addStudent(@RequestBody Course course) {
		
		if(course == null) {
			LOG.warn(" --- 201 No Content in post/course/add ---");
			return new ResponseEntity<Course>(HttpStatus.NO_CONTENT);
		}
		LOG.info(String.format(" --- Start add course %s from DB ---", course.getName()));
		
		
		course.setName(course.getName().toUpperCase());
		System.err.println(course);
		//todo add information in student_enrolled table
		
		service.addCourse(course);
		
		LOG.info(String.format(" ---End add course %s from DB ---", course.getName()));
		
		
		return new ResponseEntity<Course>(new HttpHeaders(), HttpStatus.CREATED);
		
	}
	
	@PostMapping(value = "/post/courses/add")
	public ResponseEntity<List<Course>> addCourses(@RequestBody List<Course> courses) {
		
		if(courses == null) {
			return new ResponseEntity<List<Course>>(HttpStatus.NO_CONTENT);
		}
				
		for(Course c: courses)
			c.setName(c.getName().toUpperCase());
						
		service.addCourses(courses);
		
		return new ResponseEntity<List<Course>>(new HttpHeaders(), HttpStatus.CREATED);
		
	}

}
