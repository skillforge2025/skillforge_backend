package com.skillforge.service;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.skillforge.dao.CourseDao;
import com.skillforge.dto.CourseDTO;
import com.skillforge.entity.Category;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class HomeServiceImp implements HomeService {
	private final ModelMapper modelMapper;
	private final CourseDao courseDao;

	@Override
	public List<CourseDTO> courseList() {
		// TODO Auto-generated method stub
		List<CourseDTO> courses = courseDao.getAllCourse().stream().map(course -> modelMapper.map(course, CourseDTO.class))
				.toList();
		return courses;
	}

	@Override
	public List<String> categoryList() {
		// TODO Auto-generated method stub
		List<String> categories = Arrays.stream(Category.values()).map(category -> category.toString()).toList();
		return categories;
	}
	
	
}
