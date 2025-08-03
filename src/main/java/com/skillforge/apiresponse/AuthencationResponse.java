package com.skillforge.apiresponse;

import java.time.LocalDateTime;

public class AuthencationResponse {
	private String jwtToken;
	private LocalDateTime localDateTime;

	public AuthencationResponse(String jwtToken) {
		this.jwtToken = jwtToken;
		localDateTime = LocalDateTime.now();
	}
}
