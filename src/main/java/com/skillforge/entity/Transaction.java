package com.skillforge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="transactions")
@Getter
@Setter
@AllArgsConstructor
public class Transaction extends BaseEntity{
	private String razorpayPaymentId;
	@OneToOne
	@JoinColumn(name="student_id",nullable = false)
	private Student student;
	@OneToOne
	@JoinColumn(name="course_id",nullable = false)
	private Course course;	
	private int amount;
}
