package com.skillforge.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillforge.entity.Course;

@Repository
public interface HomeDao extends JpaRepository<Course, Long> {

//	List<Course> getAllCourse();

}
