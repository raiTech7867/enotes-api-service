package com.raiTech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
public class EnotesApiBackendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnotesApiBackendServiceApplication.class, args);
	}
}
