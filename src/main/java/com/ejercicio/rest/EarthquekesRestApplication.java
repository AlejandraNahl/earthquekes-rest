package com.ejercicio.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EarthquekesRestApplication {
	
	private static final Logger log = LoggerFactory.getLogger(EarthquekesRestApplication.class);

	public static void main(String[] args) {
		log.info("Starting application EarthquekesRestApplication");
		SpringApplication.run(EarthquekesRestApplication.class, args);
	}

}
