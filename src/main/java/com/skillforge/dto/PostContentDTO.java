package com.skillforge.dto;


import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostContentDTO {
	private String tittle;
	private String description;
	private MultipartFile videoFile;
}
