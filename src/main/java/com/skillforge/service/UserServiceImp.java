package com.skillforge.service;



import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


import com.skillforge.customexception.InvalidInputException;
import com.skillforge.dao.UserDao;
import com.skillforge.dto.PostUserDTO;
import com.skillforge.entity.Role;
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

	@Override
	public void registerUser(PostUserDTO userDto) {
		System.out.println(userDto.getEmail() + userDto.getRole());
		// TODO Auto-generated method stub
		if (userDao.existsByEmail(userDto.getEmail()))
			throw new InvalidInputException("email already exists");
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user=modelMapper.map(userDto, User.class);
		user.setRole(Role.valueOf(userDto.getRole().toUpperCase()));
		 userDao.save(user);
	}




}
