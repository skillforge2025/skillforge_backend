package com.skillforge.service;

import com.skillforge.customexception.ApiResponse;
import com.skillforge.dto.InstructorDTO;
import com.skillforge.dto.PostContentDTO;
import com.skillforge.dto.PostCourseDTO;
import com.skillforge.dto.PostInstructorDTO;

public interface InstructorService {
	InstructorDTO insertDetails(PostInstructorDTO instructorDto);

	InstructorDTO getInstructorDetails(Long id);

	public ApiResponse addNewCourse(Long instructorId, PostCourseDTO courseDto);

	ApiResponse addCourseContent(Long courseId, PostContentDTO contentInfo);

	ApiResponse deleteCourse(Long instructorId,Long courseId);

	ApiResponse deleteContent(Long courseId,Long contentId);
}
