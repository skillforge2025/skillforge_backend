package com.skillforge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillforge.dto.PostContentDTO;
import com.skillforge.dto.PostCourseDTO;
import com.skillforge.dto.PostInstructorDTO;
import com.skillforge.service.InstructorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/instructor")
@AllArgsConstructor
public class InstructorController {
	private final InstructorService instructorService;

	@PostMapping
	public ResponseEntity<?> addInstructorDetails(@RequestBody PostInstructorDTO instructorDto) {
		return ResponseEntity.ok(instructorService.insertDetails(instructorDto));
	}

	@GetMapping
	public ResponseEntity<?> getInstructorDetails(@RequestHeader Long instructorId) {
		return ResponseEntity.ok(instructorService.getInstructorDetails(instructorId));
	}

	@PostMapping("/course")
	public ResponseEntity<?> addNewCourse(@RequestHeader Long instructorId, @RequestBody PostCourseDTO course) {
		return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.addNewCourse(instructorId, course));
	}

	@DeleteMapping("/course")
	public ResponseEntity<?> deleteCourse(@RequestHeader Long instructorId, @RequestHeader Long courseId) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(instructorService.deleteCourse(instructorId, courseId));
	}

	@PostMapping("/course/content")
	public ResponseEntity<?> addContent(@RequestHeader Long courseId, @RequestBody PostContentDTO contentInfo) {
		return ResponseEntity.ok(instructorService.addCourseContent(courseId, contentInfo));
	}
	
	@DeleteMapping("/course/content")
	public ResponseEntity<?> deleteContent(@RequestHeader Long courseId,@RequestHeader Long contentId){
		return ResponseEntity.ok(instructorService.deleteContent(courseId, contentId));
	}
}
