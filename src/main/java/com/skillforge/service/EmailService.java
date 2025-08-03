package com.skillforge.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.skillforge.entity.OtpInfo;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender mailSender;

    private static Map<String, OtpInfo> otpStorage = new HashMap<>();

    public void generateAndSendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(999999));
        long expirationTime = System.currentTimeMillis() + 5 * 60 * 1000; // 5 min

        otpStorage.put(email, new OtpInfo(email, otp, expirationTime));

        sendOtpEmail(email, otp);
    }

    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + "\nIt is valid for 5 minutes.");
        mailSender.send(message);
    }

    public boolean validateOtp(String email, String inputOtp) {
        OtpInfo otpInfo = otpStorage.get(email);
        if (otpInfo == null) return false;

        boolean valid = otpInfo.getOtp().equals(inputOtp) &&
                        otpInfo.getExpirationTime() >= System.currentTimeMillis();

        if (valid) otpStorage.remove(email); // Invalidate OTP after success
        return valid;
    }
}
