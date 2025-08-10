package com.skillforge.controller;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.skillforge.apiresponse.ApiResponse;
import com.skillforge.dto.LogInDTO;
import com.skillforge.dto.OtpCredentials;
import com.skillforge.dto.PostUserDTO;
import com.skillforge.dto.UserDTO;
import com.skillforge.entity.User;
import com.skillforge.security.JwtUtils;
import com.skillforge.service.EmailService;
import com.skillforge.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	private final EmailService emailService;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtUtils jwtUtils;
	private final ModelMapper modelMapper;

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody PostUserDTO user) {
		System.out.println("usepassword" + user.getPassword());
		userService.registerUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("user created"));
	}

	@GetMapping
	public ResponseEntity<?> getUserDetails(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/login")
	public ResponseEntity<?> logIn(@RequestBody LogInDTO credentials) {
		Authentication authToken = new UsernamePasswordAuthenticationToken(credentials.getEmail(),
				credentials.getPassword());
		Authentication validAuth = authenticationManager.authenticate(authToken);
		String jwtToken = jwtUtils.generateJwtToken(validAuth);
		UserDTO user = modelMapper.map((User) validAuth.getPrincipal(), UserDTO.class);
		user.setJwtToken(jwtToken);
		return ResponseEntity.ok(user);
	}

	@PatchMapping("/update")
	public ResponseEntity<?> updateProfile(@ModelAttribute MultipartFile picture) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return ResponseEntity.ok(userService.updateProfile(email, picture));
	}

	@PostMapping("/email-login")
	public ResponseEntity<?> logInByOtp(@RequestBody OtpCredentials credentials) {
		boolean validate = emailService.validateOtp(credentials.getEmail(), credentials.getOtp());
		if (validate) {
			System.out.println("in controoller " + credentials.getEmail());
			User user = (User) userDetailsService.loadUserByUsername(credentials.getEmail());
			System.out.println(user.getPassword());
			Authentication authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext() // rets current sec ctx
					.setAuthentication(authToken);
			String jwtToken = jwtUtils.generateJwtToken(authToken);
			UserDTO authUser = modelMapper.map((User) authToken.getPrincipal(), UserDTO.class);
			authUser.setJwtToken(jwtToken);
			return ResponseEntity.ok(authUser);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("invalid otp"));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

		request.getSession().invalidate();

		return ResponseEntity.ok(new ApiResponse("log out successfully"));
	}

}
