package com.skillforge.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class Student {
	@Id
	private Long studentId;
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "student_id", referencedColumnName = "id", unique = true)
    private User userDetail;

	@OneToMany(mappedBy = "student", orphanRemoval = true, cascade = CascadeType.ALL)
	List<CoursePurchasedDetails> courseList = new ArrayList<>();
	private String certification;

}
