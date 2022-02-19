package com.develop.webapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

import com.develop.webapp.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
	
	private StudentService underTest;
	
	@Mock
	private StudentRepository repo;
	
	@BeforeEach
	void setUp() {
		underTest = new StudentServiceImpl(repo);
	}
	
	@Test
    void getStudentsTest() {
		
		//when
		underTest.getStudents();
		
		//then
		verify(repo).findAll();
    }

	@Test
	@Disabled
    void getStudentTest() {
    }

	@Test
	@Disabled
    void addStudentTest() {
    }

	@Test
	@Disabled
    void addStudentsTest() {
    }

	@Test
	@Disabled
    void deleteStudent() {
    }

}
