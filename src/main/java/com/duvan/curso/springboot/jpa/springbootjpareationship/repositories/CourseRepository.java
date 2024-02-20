package com.duvan.curso.springboot.jpa.springbootjpareationship.repositories;

import org.springframework.data.repository.CrudRepository;

import com.duvan.curso.springboot.jpa.springbootjpareationship.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

}
