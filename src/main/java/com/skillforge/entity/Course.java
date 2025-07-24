package com.skillforge.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="courses")
@Getter
@Setter
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="course_id")
	private Long courseId;
	@Column(length = 100,nullable = false)
	private String tittle;
	
	private Float duration;
	private String description;
	private double amount ;
	@Enumerated(EnumType.STRING)
	private Category category;
	//owner
	@ManyToOne
	@JoinColumn(name="instructor_id")
	private Instructor instructor;
	
	@OneToMany(mappedBy = "course",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Content>contents=new ArrayList<>();
}
