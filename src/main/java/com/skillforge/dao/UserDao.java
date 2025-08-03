package com.skillforge.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillforge.entity.User;

public interface UserDao extends JpaRepository<User, Long> {
	Optional<User>findByEmail(String email);

	boolean existsByEmail(String emial);
	
}
