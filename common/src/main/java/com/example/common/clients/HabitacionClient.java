package com.example.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.common.dto.HabitacionResponse;

//import com.example.common.dto.habitacion.HabitacionResponse;

@FeignClient(name = "habitaciones-mvs")
public interface HabitacionClient {
	
	
	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);
	
	
	@PutMapping("/{id}/estado/{idEstado}")
	void cambiarEstadoHabitacion(@PathVariable Long id, @PathVariable Long idEstado);

}
