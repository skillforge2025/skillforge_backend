package com.skillforge.controller;

import java.security.PublicKey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillforge.dto.PostStudentDTO;
import com.skillforge.entity.User;
import com.skillforge.service.StudentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {
	private final StudentService studentServivce;

	@PreAuthorize("hasRole('STUDENT')")
	@GetMapping
	public ResponseEntity<?> getStudentDetails(Authentication authencticated) {
		User user = (User) authencticated.getPrincipal();
		System.out.println(user.getId());
		return ResponseEntity.ok(studentServivce.studentDetails(user.getId()));
	}

	@PostMapping
	public ResponseEntity<?> addStudentDetails(@RequestBody PostStudentDTO student) {
		return ResponseEntity.status(HttpStatus.CREATED).body(studentServivce.addStudentDetails(student));
	}

	// Purchase course
	@PreAuthorize("hasRole('STUDENT')")
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteStudent(Authentication authenticated) {
		User user = (User) authenticated.getPrincipal();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(studentServivce.deleteStudent(user.getId()));
	}
}
