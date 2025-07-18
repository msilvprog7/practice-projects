package com.msnider.shortidgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShortidgeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortidgeneratorApplication.class, args);
	}	

}
