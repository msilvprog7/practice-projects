package com.msnider.otplocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OtplockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtplockerApplication.class, args);
	}

}
