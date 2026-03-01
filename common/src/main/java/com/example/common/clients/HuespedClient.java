package com.example.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.common.dto.huesped.HuespedResponse;

@FeignClient(name = "huespedes-mvs")
public interface HuespedClient {
	
	@GetMapping("/{id}")
	HuespedResponse  obtenerHuespedPorId(@PathVariable Long id);

}