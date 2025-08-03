package com.skillforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skillforge.service.EmailService;

@RestController
@RequestMapping("/otp")
public class EmailValidationController {

	@Autowired
	private EmailService otpService;
		@PostMapping("/send")
	public ResponseEntity<String> sendOtp(@RequestParam String email) {
		otpService.generateAndSendOtp(email);
		return ResponseEntity.ok("OTP sent to email");
	}

	@PostMapping("/verify")
	public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
		boolean valid = otpService.validateOtp(email, otp);
		return valid ? ResponseEntity.ok("OTP verified successfully")
				: ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP");
	}
}
