package com.threektechone.resorthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ResortHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResortHubApplication.class, args);
	}

}
