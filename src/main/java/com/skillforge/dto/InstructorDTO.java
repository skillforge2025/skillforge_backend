package com.skillforge.dto;

import java.util.List;

import com.skillforge.entity.Course;
import com.skillforge.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorDTO {
	private Long instructorId;
	private String bio;
	private String expertise;
	private User userDetails;
	private List<Course> courses;
}
