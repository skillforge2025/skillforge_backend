package com.skillforge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentOrderDTO {
	private float amount;
	private Long UserId;
	private Long courseId;
}
