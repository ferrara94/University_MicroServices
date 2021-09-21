package com.develop.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.develop.courses.entity.Course;


@Repository
public interface CourseRepository extends JpaRepository<Course, String>{

}
