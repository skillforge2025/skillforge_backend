package com.skillforge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillforge.service.HomeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {
		private final HomeService homeService;
		//1.get course list 
		//2.get course category
		//3.get user if present 
		//4.about
		@GetMapping
		public ResponseEntity<?> courseList(){
			return ResponseEntity.ok(homeService.courseList());
		}
		@GetMapping("/categories")
		public ResponseEntity<?> getCategories(){
			return ResponseEntity.ok(homeService.categoryList());
		}
		
		//for user details use student apis to show in header
}
