package com.mercadolibre.mutant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class MutantLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutantLabApplication.class, args);
	}

}
