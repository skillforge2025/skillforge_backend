package com.skillforge.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.skillforge.apiresponse.ApiResponse;
import com.skillforge.customexception.InvalidInputException;
import com.skillforge.customexception.UserNotFoundException;
import com.skillforge.dao.StudentDao;
import com.skillforge.dao.UserDao;
import com.skillforge.dto.PostUserDTO;
import com.skillforge.entity.Role;
import com.skillforge.entity.Student;
import com.skillforge.entity.User;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImp implements UserService {

	private UserDao userDao;
	private ModelMapper modelMapper;
	private PasswordEncoder passwordEncoder;
	private VideoService videoService;
	private StudentDao studentDao;

	@Override
	public void registerUser(PostUserDTO userDto) {
		System.out.println(userDto.getEmail() + userDto.getRole());
		// TODO Auto-generated method stub
		if (userDao.existsByEmail(userDto.getEmail()))
			throw new InvalidInputException("email already exists");

		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user = modelMapper.map(userDto, User.class);
		user.setRole(Role.valueOf(userDto.getRole().toUpperCase()));
		if (user.getRole().equals(Role.valueOf("STUDENT"))) {
			Student student = new Student();
			student.setUserDetail(user);
			studentDao.save(student);
		}
		userDao.save(user);
	}

	@Override
	public ApiResponse updateProfile(String email, MultipartFile picture) {
		// TODO Auto-generated method stub
		User user = userDao.findByEmail(email).orElseThrow(() -> new UserNotFoundException("invalid user"));
		String contentType = picture.getContentType();
		if (!contentType.equals("jpg") && !contentType.equals("png"))
			throw new IllegalArgumentException("upload image type");
		String url = videoService.uploadImage(picture);
		if (url == null || url.isBlank())
			throw new RuntimeException("Video upload failed");
		user.setImageUrl(url);
		userDao.save(user);
		return new ApiResponse("image uploaded successfully");
	}

}
