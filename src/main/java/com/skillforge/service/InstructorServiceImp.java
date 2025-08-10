package com.skillforge.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.skillforge.apiresponse.ApiResponse;
import com.skillforge.customexception.ResourceNotFoundException;
import com.skillforge.customexception.UserNotFoundException;
import com.skillforge.dao.CourseDao;
import com.skillforge.dao.InstructorDao;
import com.skillforge.dao.UserDao;
import com.skillforge.dto.InstructorCourseResponseDTO;
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
private final UtilService utilService;
	@Override
	public InstructorDTO insertDetails(String email, PostInstructorDTO instructorDto) {

		User user = userDao.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User with email " + email + " doesn't exist"));
		user.setRole(Role.INSTRUCTOR);

		Instructor instructor = new Instructor();
		instructor.setBio(instructorDto.getBio());
		instructor.setExpertise(instructorDto.getExpertise());
		instructor.setUserDetails(user);

		Instructor savedInstructor = instructorDao.save(instructor);

		return modelMapper.map(savedInstructor, InstructorDTO.class);
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
		if (courseDto.getImage() == null)
			throw new IllegalArgumentException("Thumbnail image is required");

		
		String imgUrl = videoServcie.uploadImage(courseDto.getImage());

		Course course = modelMapper.map(courseDto, Course.class);
		course.setThumbnail(imgUrl);
		course.setInstructor(instructor);
		double duration=0.0;
//		for (PostContentDTO contentDto : courseDto.getContents()) {
//			MultipartFile file = contentDto.getVideoFile();
//			if (file == null || file.getContentType() == null)
//				throw new IllegalArgumentException("Video file is required");
//
//			String contentType = file.getContentType();
//			if (!contentType.equals("video/mp4") && !contentType.equals("video/x-matroska"))
//				throw new IllegalArgumentException("Only .mp4 and .mkv videos allowed");
//
//			String videoUrl = videoServcie.uploadVideo(file);
//			if (videoUrl == null || videoUrl.isBlank())
//				throw new RuntimeException("Video upload failed");
//			duration+=utilService.getVideoLengthInMinutes(file);
//			Content content = modelMapper.map(contentDto, Content.class);
//			content.setUrl(videoUrl);
//			course.getContents().add(content);
//		}
		course.setDuration(duration);
		courseDao.save(course);
		return new ApiResponse("Course created successfully with contents");
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
	public ApiResponse addCourseContent( PostContentDTO contentInfo) {
		// secuiry + uploading video
		Course course = courseDao.findById(contentInfo.getCourseId())
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
		} catch (IllegalArgumentException e) {
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

	@Override
	public List<InstructorCourseResponseDTO> getInstructorCourses(Long id) {
		// TODO Auto-generated method stub
		Instructor instructor=instructorDao.findById(id).orElseThrow(()->new UserNotFoundException("user doesn't exit"));
		List<InstructorCourseResponseDTO>courses=instructor.getCourses().stream().map(course->modelMapper.map(course, InstructorCourseResponseDTO.class)).toList();
		return courses;
	}
}
