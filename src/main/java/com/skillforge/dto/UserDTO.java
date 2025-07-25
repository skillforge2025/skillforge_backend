package com.skillforge.dto;

import com.skillforge.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private Long id;
	private String userName;
	private String email;
	private String password;
	private Role role;
}
