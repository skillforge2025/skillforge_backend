package com.skillforge.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.skillforge.customexception.UserNotFoundException;
import com.skillforge.dao.StudentDao;
import com.skillforge.dao.UserDao;
import com.skillforge.dto.PostStudentDTO;
import com.skillforge.dto.StudentDTO;
import com.skillforge.entity.Student;
import com.skillforge.entity.User;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class StudentServiceImp implements StudentService {

	private final StudentDao studentDao;
	private final UserDao userDao;
	private final ModelMapper modelMapper;

	// check validation is authenticated user
	@Override
	public StudentDTO studentDetails(Long id) {
		Student student = studentDao.findById(id).orElseThrow(() -> new UserNotFoundException("user Not found"));

		return modelMapper.map(student, StudentDTO.class);
	}

	@Override
	public StudentDTO addStudentDetails(PostStudentDTO studentDto) {
		User user = userDao.findById(studentDto.getStudentId())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		Student student = new Student();
		student.setUserDetail(user);
		student.setCertification(studentDto.getCertification());
		Student saved = studentDao.save(student);
		return modelMapper.map(saved, StudentDTO.class);
	}

}
