package com.skillforge.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.skillforge.customexception.ResourceNotFoundException;
import com.skillforge.dao.StudentDao;
import com.skillforge.dto.StudentDTO;
import com.skillforge.entity.Student;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class StudentServiceImp implements StudentService {

	private final StudentDao studentDao;
	private final ModelMapper modelMapper;
	//check validation is authenticated user 
	@Override
	public StudentDTO studentDetails(Long id) {
		Optional<Student>optionalStudent=studentDao.findById(id);
		if(!optionalStudent.isEmpty())return modelMapper.map(optionalStudent.get(), StudentDTO.class);
		throw new ResourceNotFoundException("student doesnt exist"); 
	}

}
