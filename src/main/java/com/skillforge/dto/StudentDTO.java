package com.skillforge.dto;


import java.util.List;

import com.skillforge.entity.CoursePurchasedDetails;
import com.skillforge.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
	
	private Long studentId;
	private String certification;
	private User userDetail;
	List<CoursePurchasedDetails> courseList;
}
