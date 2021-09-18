package com.develop.webapp.repository;

import com.develop.webapp.entity.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	@Query(value = "SELECT COUNT(*) FROM STUDENTS", nativeQuery = true)
	Long countStudents();
	
}
