package com.skillforge.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="course_details")
@Getter
@Setter
public class CoursePurchasedDetails extends BaseEntity {
		@ManyToOne
		@JoinColumn(name="student_id",nullable = false)
		private Student student;
		@OneToOne
		@JoinColumn(name = "course_id", nullable = false)
		private Course course;
		@Column(nullable = false)
		private LocalDate expiry;
}
