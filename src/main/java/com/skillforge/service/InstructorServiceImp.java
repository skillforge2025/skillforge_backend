package com.skillforge.service;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.skillforge.apiresponse.ApiResponse;
import com.skillforge.customexception.ResourceNotFoundException;
import com.skillforge.customexception.UserNotFoundException;
import com.skillforge.dao.CourseDao;
import com.skillforge.dao.InstructorDao;
import com.skillforge.dao.UserDao;
import com.skillforge.dto.InstructorDTO;
import com.skillforge.dto.PostContentDTO;
import com.skillforge.dto.PostCourseDTO;
import com.skillforge.dto.PostInstructorDTO;
import com.skillforge.dto.UserDTO;
import com.skillforge.entity.Content;
import com.skillforge.entity.Course;
import com.skillforge.entity.Instructor;
import com.skillforge.entity.Role;
import com.skillforge.entity.User;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class InstructorServiceImp implements InstructorService {
	private final InstructorDao instructorDao;
	private final UserDao userDao;
	private final CourseDao courseDao;
	private final VideoService videoServcie;
	private final ModelMapper modelMapper;

	@Override
	public InstructorDTO insertDetails(PostInstructorDTO instructorDto) {
		User user = userDao.findById(instructorDto.getInstructorId())
				.orElseThrow(() -> new UserNotFoundException("user doesn't exit"));
		if (Role.valueOf("INSTRUCTOR").equals(user.getRole())) {
			Instructor instructor = new Instructor();
			instructor.setBio(instructorDto.getBio());
			instructor.setExpertise(instructor.getExpertise());
			instructor.setUserDetails(user);
			Instructor persistInstructor = instructorDao.save(instructor);
			return modelMapper.map(persistInstructor, InstructorDTO.class);
		}
		throw new UserNotFoundException("instructor doesn't exit");
	}

	@Override
	public InstructorDTO getInstructorDetails(Long id) {
		Instructor instuctor = instructorDao.findById(id)
				.orElseThrow(() -> new UserNotFoundException("instructor not found"));
		UserDTO instructorInfo = modelMapper.map(instuctor.getUserDetails(), UserDTO.class);
		InstructorDTO instructorDto = modelMapper.map(instuctor, InstructorDTO.class);
		instructorDto.setUserDetails(instructorInfo);
		return instructorDto;
	}

	@Override
	public ApiResponse addNewCourse(Long instructorId, PostCourseDTO courseDto) {
		// check authenticated and authorized //solution for get email via
		// authentication and check role after that get id

		Instructor instructor = instructorDao.findById(instructorId)
				.orElseThrow(() -> new UserNotFoundException("instructor doesnt't exits"));
		Course course = modelMapper.map(courseDto, Course.class);
//		course.setInstructor(instructor);
		course.setInstructor(instructor);
		instructor.getCourses().add(course);
		instructorDao.save(instructor);
//		courseDao.save(course);
		return new ApiResponse("course created successfull add content");
	}

	// incomplete api
	@Override
	public ApiResponse deleteCourse(Long instructorId, Long courseId) {
		// perform authentication
		Instructor instructor = instructorDao.findById(instructorId)
				.orElseThrow(() -> new UserNotFoundException("instructor not found"));
//		instructor.getCourses(
		courseDao.deleteById(courseId);
		return new ApiResponse("course delete successfully");
	}

	@Override
	public ApiResponse addCourseContent(Long courseId, PostContentDTO contentInfo) {
		// secuiry + uploading video
		Course course = courseDao.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("course doesn't exit"));

		Content content = modelMapper.map(contentInfo, Content.class);

		content.setCourse(course);
		// add url link
		String contentType = contentInfo.getVideoFile().getContentType();
		try {
			if (!contentType.equals("video/mp4") && !contentType.equals("video/mkv"))
				throw new IllegalArgumentException("invalid file ,choose video file");
			String url = videoServcie.uploadVideo(contentInfo.getVideoFile());// uploading video
			content.setUrl(url);
			course.getContents().add(content);
		} catch (IllegalArgumentException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return new ApiResponse("content added successfully");
	}

	@Override
	public ApiResponse deleteContent(Long courseId, Long contentId) {
		Course course = courseDao.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("course doesn't exist"));
		List<Content> contents = course.getContents();
		boolean removed = contents.removeIf(content -> content.getContentId().equals(contentId));
		if (!removed)
			throw new ResourceNotFoundException("content doesn't exit");
		return new ApiResponse("deleted successfully");
	}
}
