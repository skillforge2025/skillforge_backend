package com.skillforge.dto;

import java.util.List;

import com.skillforge.entity.Category;
import com.skillforge.entity.Content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorCourseResponseDTO {
	private Long courseId;
	private String tittle;
	private double duration;
	private String thumbnail;
	private String description;
	private double amount;
	private Category category;
	private List<Content> contents;
}
