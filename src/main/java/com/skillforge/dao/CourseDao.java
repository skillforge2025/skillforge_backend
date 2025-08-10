package com.skillforge.dao;

import java.util.List;
import com.skillforge.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillforge.entity.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {

	@Query("SELECT c FROM Course c")
	List<Course> getAllCourse();

	List<Course> findAllByCategory(Category category);
	@Query("SELECT c FROM Course  c WHERE LOWER(c.tittle) LIKE LOWER(CONCAT('%', :courseName, '%'))")
	List<Course> findAllByTittle( String courseName);


}
