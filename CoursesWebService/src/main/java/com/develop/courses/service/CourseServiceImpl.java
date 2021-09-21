package com.develop.courses.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.develop.courses.entity.Course;
import com.develop.courses.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	CourseRepository repo;

	@Override
	public List<Course> getCourses() {
		return repo.findAll();
	}

	@Override
	public void addCourses(List<Course> courses) {
		repo.saveAll(courses);
		
	}

	@Override
	public void addCourse(Course course) {
		repo.save(course);
		
	}

	@Override
	public Course getCourse(String id) {
		
		Optional<Course> c = repo.findById(id);
		
		if(c.isPresent())
			return c.get();
		return null;
	
	}

}
