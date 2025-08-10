package com.skillforge;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SkillForggeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillForggeApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper model = new ModelMapper();
		model.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
				.setPropertyCondition(Conditions.isNotNull());
		return model;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // allow all paths
						.allowedOrigins("http://localhost:5173").allowCredentials(true)// allow all origins
						.allowedMethods("*") // allow GET, POST, PUT, DELETE, etc.
						.allowedHeaders("*"); // allow all headers
			}
		};
	}

	@Bean
	 MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

}
