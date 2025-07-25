package com.skillforge.service;

import com.skillforge.dto.LogInDTO;
import com.skillforge.dto.UserDTO;


public interface UserService {
	UserDTO registerUser(UserDTO user);

	UserDTO getUserDetails(Long id);
	UserDTO  userInfo(LogInDTO credentials);
	
}
