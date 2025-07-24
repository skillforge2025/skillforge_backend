package com.skillforge.dto;

import java.util.List;

import com.skillforge.entity.Category;
import com.skillforge.entity.Content;
import com.skillforge.entity.Instructor;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CourseDTO {
	
	private Long courseId;
	private String tittle;
	private Float duration;
	private String description;
	private double amount ;
	private Category category;
	private Instructor instructor;
	private List<Content>contents;
}
