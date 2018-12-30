package com.aut.watering.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WateringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WateringApplication.class, args);
	}
}
