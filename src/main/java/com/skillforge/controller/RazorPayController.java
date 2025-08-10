package com.skillforge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillforge.dto.PaymentOrderDTO;
import com.skillforge.dto.PaymentVerificationRequest;
import com.skillforge.entity.User;
import com.skillforge.service.PurchaseService;
import com.skillforge.service.RazorPayService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class RazorPayController {
	private final RazorPayService razorPayService;
	private final PurchaseService purchaseService;

	@PostMapping("/create-order")
	public ResponseEntity<?> createOrder(@RequestBody PaymentOrderDTO request) {
		try {
			System.out.println("in create order " + request.getUserId() + request.getCourseId());
			String order = razorPayService.createOrder(request.getAmount(), request.getCourseId().toString(),
					request.getUserId().toString());
			return ResponseEntity.ok(order);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}

	@PostMapping("/verify-payment")
	public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationRequest request) {
System.out.println("in varification");
		boolean isValid = razorPayService.verifySignature(request.getRazorpayOrderId(), request.getRazorpayPaymentId(),
				request.getRazorpaySignature());
		if (isValid) {
			// 🔥 Add course to user

			return ResponseEntity.ok(purchaseService.purchaseCourse(request.getUserId(), request.getCourseId()));
		}
		return ResponseEntity.badRequest().body("Invalid user or course");
	}

}
