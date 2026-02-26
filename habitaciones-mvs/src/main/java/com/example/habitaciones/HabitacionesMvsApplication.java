package com.example.habitaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.example.habitaciones", "com.example.common",})
public class HabitacionesMvsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitacionesMvsApplication.class, args);
	}

}
