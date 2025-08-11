package com.skillforge.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfiguration {

	private final PasswordEncoder encoder;
	private final CustomJwtFilter customJwtFilter;
	private final JwtAuthEntryPoint jwtAuthEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// CORS + CSRF disabled for REST API + basic cors config
		http.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(List.of("*"));
			config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			config.setAllowedHeaders(List.of("*"));
			config.setAllowCredentials(false);
			return config;
		}));

		http.csrf(csrf -> csrf.disable());

		// Authorization rules (single declaration; order matters)
		http.authorizeHttpRequests(request -> request
				// public / swagger / auth endpoints
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/user/signup", "/user/login",
						"/user/email-login", "/otp/send", "/payment/**")
				.permitAll()

				// public GET endpoints
				.requestMatchers(HttpMethod.GET, "/").permitAll().requestMatchers(HttpMethod.GET, "/category/**")
				.permitAll().requestMatchers(HttpMethod.GET, "/categories").permitAll()
				.requestMatchers(HttpMethod.GET, "/course/search/**").permitAll()
				// role based endpoints
				.requestMatchers(HttpMethod.GET, "/instructor").hasRole("INSTRUCTOR")
				.requestMatchers(HttpMethod.POST, "/instructor").hasRole("INSTRUCTOR")
				.requestMatchers(HttpMethod.GET, "/student").hasRole("STUDENT")
				.requestMatchers(HttpMethod.POST, "/student").hasRole("STUDENT")
				.requestMatchers(HttpMethod.GET, "/course").hasAnyRole("STUDENT", "INSTRUCTOR")
				.requestMatchers(HttpMethod.GET, "/payment").hasRole("STUDENT")

				// all other requests require authentication
				.anyRequest().authenticated());

		// Stateless session
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Add JWT filter before username/password filter
		http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);

		// Handle auth failures with custom entry point
		http.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration mgr) throws Exception {
		return mgr.getAuthenticationManager();
	}
}
