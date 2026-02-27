package com.example.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservas-mvs")
public interface ReservaClient {
	
	@GetMapping("/huesped/{idHuesped}/tiene-activas")
	Boolean huespedTieneReservasActivas(@PathVariable Long idHuesped);

}
