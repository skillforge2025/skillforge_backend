package com.skillforge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillforge.dto.PostContentDTO;
import com.skillforge.dto.PostCourseDTO;
import com.skillforge.dto.PostInstructorDTO;
import com.skillforge.entity.User;
import com.skillforge.service.InstructorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class InstructorController {
	private final InstructorService instructorService;

	@PostMapping
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<?> addInstructorDetails(@RequestBody PostInstructorDTO instructorDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return ResponseEntity.ok(instructorService.insertDetails(email, instructorDto));
	}

	@GetMapping
	public ResponseEntity<?> getInstructorDetails(Authentication authentication) {
		System.out.println("in instructor details ");
		User user = (User) authentication.getPrincipal();
		return ResponseEntity.ok(instructorService.getInstructorDetails(user.getId()));
	}
	
	@GetMapping("/course")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<?>getCourseDetails(Authentication authenticated){
		User user = (User) authenticated.getPrincipal();
		return ResponseEntity.ok(instructorService.getInstructorCourses(user.getId()));
	}

//	@PreAuthorize("hasRole('INSTRUCTOR')")
	@PostMapping(value = "/course", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addNewCourse(Authentication authenticated, @ModelAttribute PostCourseDTO course) {
		System.out.println("adding course");
		User user = (User) authenticated.getPrincipal();
		return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.addNewCourse(user.getId(), course));
	}

	@DeleteMapping("/course")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<?> deleteCourse(Authentication authenticaetd, @RequestHeader Long courseId) {
		User user = (User) authenticaetd.getPrincipal();
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(instructorService.deleteCourse(user.getId(), courseId));
	}

	@PostMapping(value = "/course/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addContent( @ModelAttribute PostContentDTO contentInfo) {
		System.out.println("called ");
		System.out.println(contentInfo.getDescription() + " " + contentInfo.getTittle());
		return ResponseEntity.ok(instructorService.addCourseContent( contentInfo));
	}

	@DeleteMapping("/course/content")
	public ResponseEntity<?> deleteContent(@RequestHeader Long courseId, @RequestHeader Long contentId) {
		return ResponseEntity.ok(instructorService.deleteContent(courseId, contentId));
	}
	
}
