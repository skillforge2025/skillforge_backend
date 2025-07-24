package com.skillforge.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillforge.entity.Student;

public interface StudentDao extends JpaRepository<Student, Long> {
	
}
