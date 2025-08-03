package com.skillforge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentRequestDTO {
	private Long courseId;
	private String courseName;
	private String contentId;
	private String contentName;
	private String url;
}
