package com.skillforge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OtpInfo {
	private String email;
	private String otp;
	private long expirationTime;
}
