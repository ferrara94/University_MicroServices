package com.develop.courses.service;

import java.util.List;

import com.develop.courses.entity.Course;

public interface CourseService {

	public List<Course> getCourses();
	
	public void addCourses(List<Course> courses);
	
	public void addCourse(Course course);
	
	public Course getCourse(String id);
}
