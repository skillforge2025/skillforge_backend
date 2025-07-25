package com.skillforge.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.skillforge.controller.UserController;
import com.skillforge.customexception.ApiResponse;
import com.skillforge.customexception.UserNotFoundException;
import com.skillforge.dao.UserDao;
import com.skillforge.dto.LogInDTO;
import com.skillforge.dto.UserDTO;
import com.skillforge.entity.User;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImp implements UserService {

	private UserDao userDao;
	private ModelMapper modelMapper;

	@Override
	public UserDTO registerUser(UserDTO userDto) {
		System.out.println(userDto.getEmail() + userDto.getRole());
		// TODO Auto-generated method stub
		 User persistUser=userDao.save(modelMapper.map(userDto, User.class));
		return modelMapper.map(persistUser,UserDTO.class);

	}

	// use security here
	@Override
	public UserDTO getUserDetails(Long id) {
		// TODO Auto-generated method stub
		User user = userDao.findById(id).orElseThrow(() -> new UserNotFoundException("user not found check id"));
		return modelMapper.map(user, UserDTO.class);

	}

	@Override
	public UserDTO userInfo(LogInDTO credentials) {
		Optional<User> user = userDao.findByEmail(credentials.getEmail());
		if (user.isPresent() && user.get().getPassword().equals(credentials.getPassword())) // have to use security
																							// hashing
			return modelMapper.map(user.get(), UserDTO.class);

		throw new UserNotFoundException("invalid credentials");
	}

}
