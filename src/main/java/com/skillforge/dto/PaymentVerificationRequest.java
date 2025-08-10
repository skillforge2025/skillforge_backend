package com.skillforge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentVerificationRequest {
	private String razorpayPaymentId;
	private String razorpayOrderId;
	private String razorpaySignature;
	private Long courseId;
	private Long userId;
	private int amount;
}
