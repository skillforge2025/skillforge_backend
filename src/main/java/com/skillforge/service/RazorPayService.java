package com.skillforge.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.skillforge.customexception.InvalidInputException;

@Service
public class RazorPayService {
	@Value("{razorpay.secret.id}")
	private String keyId;
	@Value("{razorpay.secret.key}")
	private String keySecret;

	public String createOrder(float amountInRupees, String courseId, String userId) throws Exception {
		RazorpayClient client = new RazorpayClient(keyId, keySecret);

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amountInRupees * 100); // in paise
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", courseId + "_" + userId + "_" + System.currentTimeMillis());
		orderRequest.put("payment_capture", true);

		Order order = client.orders.create(orderRequest);
		return order.toString();
	}

	public boolean verifySignature(String orderId, String paymentId, String signature) {
		try {
			String payload = orderId + "|" + paymentId;
			String actualSignature = hmacSha256(payload, keySecret);
			return actualSignature.equals(signature);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	private String hmacSha256(String data, String secret) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");

		mac.init(keySpec);
		byte[] result = mac.doFinal(data.getBytes());
		return Hex.encodeHexString(result);
	}

}
