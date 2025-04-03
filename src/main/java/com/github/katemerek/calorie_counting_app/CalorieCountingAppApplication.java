package com.github.katemerek.calorie_counting_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CalorieCountingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalorieCountingAppApplication.class, args);
	}

}
