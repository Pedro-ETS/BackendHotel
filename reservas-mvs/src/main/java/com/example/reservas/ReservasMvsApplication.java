package com.example.reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.common.clients")
@SpringBootApplication(scanBasePackages = {"com.example.reservas", "com.example.common",})
public class ReservasMvsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservasMvsApplication.class, args);
	}

}
