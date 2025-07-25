package com.skillforge.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillforge.entity.Instructor;

public interface InstructorDao extends JpaRepository<Instructor, Long> {

}
