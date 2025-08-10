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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import lombok.AllArgsConstructor;

@Configuration // to declare config class - to declare spring beans - @Bean)
@EnableWebSecurity // to customize spring security
@EnableMethodSecurity // to enable method level annotations
//(@PreAuthorize , @PostAuthorize..) to specify  authorization rules
@AllArgsConstructor
public class SecurityConfiguration {
	// depcy - password encoder
	private final PasswordEncoder encoder;
	private final CustomJwtFilter customJwtFilter;
	private JwtAuthEntryPoint jwtAuthEntryPoint;

	/*
	 * configure spring bean to customize spring security filter chain disable CSRF
	 * protection - session creation policy - stateless - disable form login based
	 * authentication - enable basic authentication scheme , for REST clients
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// 1. Disable CSRF protection
		 http
         .cors(cors -> cors.configurationSource(request -> {
             CorsConfiguration config = new CorsConfiguration();
             config.setAllowedOrigins(List.of("*"));
             config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
             config.setAllowedHeaders(List.of("*"));
             return config;
         }))
         .csrf(csrf -> csrf.disable())
         .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		// 2. Authenticate any request
//		http.authorizeHttpRequests(request ->
//		// 5.permit all - swagger , view all restaurants , user signin , sign up....
//		request.requestMatchers("/swagger-ui/**", "/v**/api-docs/**", "/user/signup", "/user/login",
//				"/user/email-login", "/otp/send","/payment/**").permitAll().requestMatchers(HttpMethod.GET, "/").permitAll()
//				.requestMatchers(HttpMethod.GET, "/category/**").permitAll()
//				.requestMatchers(HttpMethod.GET, "/categories").permitAll()
//				
//				.requestMatchers(HttpMethod.GET, "/instructor").hasRole("INSTRUCTOR")
//				.requestMatchers(HttpMethod.POST, "/instructor").hasRole("INSTRUCTOR")
//				.requestMatchers(HttpMethod.GET, "/student").hasRole("STUDENT")
//				.requestMatchers(HttpMethod.POST, "/student").hasRole("STUDENT")
//				.requestMatchers(HttpMethod.GET, "/course").hasAnyRole("STUDENT", "INSTRUCTOR")
//				.requestMatchers(HttpMethod.GET, "/payment").hasRole("STUDENT").anyRequest().authenticated());
//		// 3. enable HTTP basic auth
		// http.httpBasic(Customizer.withDefaults());
		// 4. set session creation policy - stateless
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// 5. add custom JWT filter before -UserNamePasswordAuthFilter
		http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
		// 6. Customize error code of SC 401 , in case of authentication failure
		http.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint));
		
		return http.build();
	}

	// configure a spring to return Spring security authentication manager
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration mgr) throws Exception {
		return mgr.getAuthenticationManager();
	}

}
