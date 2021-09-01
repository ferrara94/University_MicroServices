package com.develop.webapp.service;

import com.develop.webapp.entity.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentService  {

    public List<Student> getStudents();

    public Student getStudent(Long id);

    public void addStudent(Student student);

    public void addStudents(List<Student> students);

    public Long deleteStudent(Long id);
}
