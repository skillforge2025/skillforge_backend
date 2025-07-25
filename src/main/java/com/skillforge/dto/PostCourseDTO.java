package com.skillforge.dto;

import com.skillforge.entity.Category;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCourseDTO {
	private String tittle;
	private Float duration;
	private String description;
	private double amount;
	private Category category;
}
