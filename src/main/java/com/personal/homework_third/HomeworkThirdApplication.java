package com.personal.homework_third;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HomeworkThirdApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeworkThirdApplication.class, args);
	}

}
