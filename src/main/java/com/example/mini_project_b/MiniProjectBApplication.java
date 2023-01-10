package com.example.mini_project_b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MiniProjectBApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniProjectBApplication.class, args);
	}

}
