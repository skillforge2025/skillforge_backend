package com.skillforge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.skillforge.entity.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {

	@Query("SELECT c FROM Course c")
	List<Course> getAllCourse();

}
