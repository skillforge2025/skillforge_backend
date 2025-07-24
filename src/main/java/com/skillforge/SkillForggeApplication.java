package com.skillforge;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
}
