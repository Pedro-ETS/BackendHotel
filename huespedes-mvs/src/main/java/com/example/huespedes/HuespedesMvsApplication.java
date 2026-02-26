package com.example.huespedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.example.huespedes", "com.example.common",})
public class HuespedesMvsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuespedesMvsApplication.class, args);
	}

}