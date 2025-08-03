package com.skillforge.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.skillforge.apiresponse.ApiResponse;
import com.skillforge.customexception.ResourceNotFoundException;
import com.skillforge.customexception.UserNotFoundException;
import com.skillforge.dao.StudentDao;
import com.skillforge.dao.UserDao;
import com.skillforge.dto.ContentRequestDTO;
import com.skillforge.dto.PostStudentDTO;
import com.skillforge.dto.StudentDTO;
import com.skillforge.dto.UserDTO;
import com.skillforge.entity.CoursePurchasedDetails;
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
		StudentDTO studentDto = modelMapper.map(student, StudentDTO.class);
		studentDto.setUserDetail(modelMapper.map(student.getUserDetail(), UserDTO.class));
		return studentDto;
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

	@Override
	public ApiResponse deleteStudent(Long userId) {
		// TODO Auto-generated method stub
		Student student = studentDao.findById(userId).orElseThrow(() -> new UserNotFoundException("user doen't exit"));
		studentDao.delete(student);
		userDao.delete(student.getUserDetail());
		return new ApiResponse("user deleted successfully");
	}

}
