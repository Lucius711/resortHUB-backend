package com.threektechone.resorthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.threektechone.resorthub.config.env.DotenvConfig;

@EnableScheduling
@SpringBootApplication
public class ResortHubApplication {

	public static void main(String[] args) {
		DotenvConfig.load();
		SpringApplication.run(ResortHubApplication.class, args);
	}

}
