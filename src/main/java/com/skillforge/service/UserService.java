package com.skillforge.service;

import com.skillforge.customexception.ApiResponse;
import com.skillforge.dto.LogInDTO;
import com.skillforge.dto.UserDTO;


public interface UserService {
	ApiResponse registerUser(UserDTO user);

	UserDTO getUserDetails(Long id);
	UserDTO  userInfo(LogInDTO credentials);
	
}
