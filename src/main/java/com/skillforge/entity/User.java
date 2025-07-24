package com.skillforge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
	@Column(name = "user_Name", nullable = false, length = 50)
	private String userName;
	@Column(length = 30, unique = true, nullable = false)
	private String email;
	@Column(length = 500, nullable = false)
	private String password;
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;
	
}
