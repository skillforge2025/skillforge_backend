package com.skillforge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillforge.service.StudentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {
		private final StudentService studentServivce;
		@GetMapping
		public ResponseEntity<?>getStudentDetails(@RequestHeader Long id){
			System.out.println(id);
			return ResponseEntity.ok(studentServivce.studentDetails(id));
		}
}
