package com.skillforge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contents")
@Getter
@Setter
@EqualsAndHashCode(of = "contentId")
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
	@JoinColumn(name = "course_id", nullable = false)
	@JsonBackReference
	private Course course;
}
