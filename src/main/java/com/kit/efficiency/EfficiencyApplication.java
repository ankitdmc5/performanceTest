package com.kit.efficiency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EfficiencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfficiencyApplication.class, args);
		System.out.println("EfficiencyApplication");
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
