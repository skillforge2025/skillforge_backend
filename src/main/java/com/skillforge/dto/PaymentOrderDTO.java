package com.skillforge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentOrderDTO {
	
	private int amount;
	private Long userId;
	private Long courseId;
}
