package com.develop.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop.webapp.entity.Student.Student;
import com.develop.webapp.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("api/students")
public class StudentController {

    @Autowired
    StudentService service;

    @GetMapping(value = "index")
    public String getIndex() {
        return "rest controller ok";
    }

    @GetMapping(value = "get/all")
    public ResponseEntity<?> getStudents() {

    	List<Student> students = service.getStudents();

        if(students.isEmpty()) {

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseNode = mapper.createObjectNode();

            responseNode.put("code", HttpStatus.OK.toString());
            responseNode.put("message", "Students list is empty");

            return new ResponseEntity<>(responseNode, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Student>>(students,HttpStatus.OK);

    }
    
    @PostMapping(value = "post/student")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
		
    	if(student == null || student.getId() == null ||student.getName().isBlank()) {

    		ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseNode = mapper.createObjectNode();

            responseNode.put("code", HttpStatus.BAD_REQUEST.toString());
            responseNode.put("message", "Fields not entered properly");

            return new ResponseEntity<>(responseNode, HttpStatus.BAD_REQUEST);
        }
    	
						
		service.addStudent(student);
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
		
	}
    
    @PostMapping(value = "post/students")
	public ResponseEntity<?> addUniversity(@RequestBody List<Student> students) {
		
		if(students == null || students.isEmpty()) {
			
			ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseNode = mapper.createObjectNode();

            responseNode.put("code", HttpStatus.BAD_REQUEST.toString());
            responseNode.put("message", "Students list is empty or null");

            return new ResponseEntity<>(responseNode, HttpStatus.BAD_REQUEST);
       	}
				
			
		service.addStudents(students);
		
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
		
	}


}
