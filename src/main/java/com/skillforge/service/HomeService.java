package com.skillforge.service;

import java.util.List;

import com.skillforge.dto.CourseDTO;

public interface HomeService {

	List<CourseDTO> courseList();
	List<String>categoryList();
	List<CourseDTO> courseByCategory(String category);
	
	List<CourseDTO> searchCourse(String courseName);
	
}
