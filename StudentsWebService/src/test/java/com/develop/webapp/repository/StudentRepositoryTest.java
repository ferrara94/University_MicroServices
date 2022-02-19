package com.develop.webapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.develop.webapp.entity.Student.Student;

@DataJpaTest
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository underTest;
	
	@AfterEach
	void tearDown() {
		underTest.deleteAll();
	}
	
	@Test
	void countStudentsTest() {
		//given
		long id = 61270;
		Student student = new Student(
					id,
					"Mark",
					"Lolle",
					"Salerno",
					true
				);
		underTest.save(student);
		
		//when
		long numbers = underTest.count();
		
		//then
		assertEquals(1,numbers);
	}
	
}
