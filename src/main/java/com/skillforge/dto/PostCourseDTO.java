package com.skillforge.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.skillforge.entity.Category;
import com.skillforge.entity.Levels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCourseDTO {
	private String tittle;
	private Category category;
	private double amount;
	private Levels level;
	private MultipartFile  image;
	private String description;
	private Float duration;
}
