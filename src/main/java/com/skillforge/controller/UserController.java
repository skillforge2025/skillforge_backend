package com.skillforge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillforge.dto.LogInDTO;
import com.skillforge.dto.UserDTO;
import com.skillforge.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

   
	private final UserService userService;


	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserDTO user) {
		
		return ResponseEntity.ok(userService.registerUser(user));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?>getUserDetails(@PathVariable Long id){
		return ResponseEntity.ok(userService.getUserDetails(id));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?>logIn(@RequestBody LogInDTO credentials){
		return ResponseEntity.ok(userService.userInfo(credentials));
	}
}
