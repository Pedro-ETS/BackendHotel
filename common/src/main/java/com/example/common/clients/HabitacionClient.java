package com.example.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.common.dto.habitacion.HabitacionResponse;


@FeignClient(name = "habitaciones-mvs")
public interface HabitacionClient {
	
	
	@GetMapping("/{id}")
	HabitacionResponse  obtenerHabitacionPorId(@PathVariable Long id);
	
	
	@PatchMapping("/{id}/estado/{idEstado}")
	void cambiarEstadoHabitacion(@PathVariable Long id, @PathVariable Long idEstado);
	
	@GetMapping("/habitacion/{idHabitacion}/tiene-activas")
	Boolean habitacionTieneReservasActivas(@PathVariable Long idHabitacion);
	
	 //Endpoint que solo se usa para liberar la habitación al finalizar o cancelar una reserva.
	@PatchMapping("/{id}/estado-interno/{idEstado}")
	HabitacionResponse liberarHabitacionDesdeReserva(@PathVariable Long id,@PathVariable Long idEstado);
	

}
