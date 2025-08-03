package com.skillforge.dto;

import com.skillforge.entity.Role;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PostUserDTO {
	private Long id;
	private String userName;
	private String email;
	private String password;
	private String role;
}
