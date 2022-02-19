package com.develop.webapp.service;

import com.develop.webapp.entity.Student.Student;
import com.develop.webapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepository repo;
    
    public  StudentServiceImpl() {
    	
    }
    
    public  StudentServiceImpl(StudentRepository repo) {
    	this.repo = repo;
	}

    @Override
    public List<Student> getStudents() {
        return repo.findAll();
    }

    @Override
    public Student getStudent(Long id) {

        Optional<Student> student = repo.findById(id);

        if(student.isPresent()) return student.get();
        else return null;
    }

    @Override
    public void addStudent(Student student) {
        repo.save(student);
    }

    @Override
    public void addStudents(List<Student> students) {
        repo.saveAll(students);
    }

    @Override
    public Long deleteStudent(Long id) {
        repo.deleteById(id);
        return id;
    }
}
