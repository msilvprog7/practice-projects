package com.msnider.K8sSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@RequestMapping("/api")
public class K8sSpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(K8sSpringBootApplication.class, args);
	}

	@GetMapping("/hello")
	public String get(@RequestParam(required = false) String name) {
		return (name == null || name.trim().isEmpty()) ? "Hi!" : "Hi, " + name + "!";
	}
}
