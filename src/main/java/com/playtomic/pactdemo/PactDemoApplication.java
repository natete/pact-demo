package com.playtomic.pactdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PactDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PactDemoApplication.class, args);
	}

}
