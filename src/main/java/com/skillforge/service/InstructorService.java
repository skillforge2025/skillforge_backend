package com.skillforge.service;


import java.util.List;

import com.skillforge.apiresponse.ApiResponse;
import com.skillforge.dto.InstructorCourseResponseDTO;
import com.skillforge.dto.InstructorDTO;
import com.skillforge.dto.PostContentDTO;
import com.skillforge.dto.PostCourseDTO;
import com.skillforge.dto.PostInstructorDTO;

public interface InstructorService {
	InstructorDTO insertDetails(String email, PostInstructorDTO instructorDto);

	InstructorDTO getInstructorDetails(Long id);

	 ApiResponse addNewCourse(Long instructorId, PostCourseDTO courseDto);

	ApiResponse addCourseContent( PostContentDTO contentInfo);

	ApiResponse deleteCourse(Long instructorId,Long courseId);

	ApiResponse deleteContent(Long courseId,Long contentId);

	List<InstructorCourseResponseDTO> getInstructorCourses(Long id);
}
