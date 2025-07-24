package com.skillforge.customexception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
	private String msg;
	private LocalDateTime createdAt;
	public ApiResponse(String msg) {
		this.msg=msg;
		createdAt=LocalDateTime.now();
	}
}
