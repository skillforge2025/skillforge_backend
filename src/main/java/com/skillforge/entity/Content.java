package com.skillforge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="contents")
public class Content {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long contentId;
		
		private String tittle;
		@Column(nullable = false)
		private String url;
		@Column(nullable = true)
		private String description;
		@ManyToOne
		@JoinColumn(name="course_id",nullable = false)
		private Course course;
}
