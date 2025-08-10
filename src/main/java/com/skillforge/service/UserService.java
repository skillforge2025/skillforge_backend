package com.skillforge.service;

import org.springframework.web.multipart.MultipartFile;

import com.skillforge.apiresponse.ApiResponse;
import com.skillforge.dto.PostUserDTO;

public interface UserService {
	void registerUser(PostUserDTO user);

	ApiResponse updateProfile(String email, MultipartFile picture);

}
