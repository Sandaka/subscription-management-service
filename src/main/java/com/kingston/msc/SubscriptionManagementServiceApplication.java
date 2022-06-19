package com.kingston.msc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SubscriptionManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionManagementServiceApplication.class, args);
	}

}
