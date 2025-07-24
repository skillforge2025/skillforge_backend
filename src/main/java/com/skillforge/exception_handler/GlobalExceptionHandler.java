package com.skillforge.exception_handler;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.skillforge.customexception.ApiResponse;
import com.skillforge.customexception.ResourceNotFoundException;
import com.skillforge.customexception.UserNotFoundException;

import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUserNotFound(UserNotFoundException e) {
		System.out.println("in method arg invalid " + e);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
	}
}
