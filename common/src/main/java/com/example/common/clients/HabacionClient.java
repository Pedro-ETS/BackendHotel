package com.example.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "habitaciones-msv")
public interface HabacionClient {
	
	/*
	@GetMapping("/{id}")
	HabitacionResponse obtenerHabiatacionPorId(@PathVariable Long id);
	*/
	
	@PatchMapping("/{idReserva}/estado/{idEstado}")
	void cambiarEstadoHabitacion(@PathVariable Long id);

}
