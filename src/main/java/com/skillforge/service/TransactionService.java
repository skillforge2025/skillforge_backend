package com.skillforge.service;

import com.skillforge.dto.PaymentVerificationRequest;

public interface TransactionService {
		void saveTransaction(PaymentVerificationRequest request);
}
