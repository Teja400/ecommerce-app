package com.blueyonder.ecomapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EcomappRegistryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcomappRegistryServiceApplication.class, args);
	}
}
