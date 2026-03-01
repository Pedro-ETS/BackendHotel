package com.example.reservas.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.controller.CommonController;
import com.example.common.dto.ReservaRequest;
import com.example.common.dto.ReservaResponse;
import com.example.reservas.services.ReservaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ReservaController extends CommonController<ReservaRequest, ReservaResponse, ReservaService> {
	 
	public ReservaController(ReservaService service) {
	        super(service);
	    }

	@PatchMapping("/{idReserva}/estado/{idEstado}")
    public ResponseEntity<ReservaResponse> cambiarEstado(@PathVariable Long idReserva,
            @PathVariable Integer idEstado) {log.info("Cambiando estado de reserva {} a {}", idReserva, idEstado);
            return ResponseEntity.ok(service.cambiarEstado(idReserva, idEstado));
    }
    
    
	@GetMapping("/huesped/{idHuesped}/tiene-activas")
    public ResponseEntity<Boolean> huespedTieneReservasActivas(
            @PathVariable Long idHuesped) {
		return ResponseEntity.ok(service.huespedTieneReservasActivas(idHuesped));
    }
    
	@GetMapping("/habitaciones/{idHabitacion}/tiene-activas")
    public ResponseEntity<Boolean> habitacionTieneReservasActivas(
            @PathVariable Long idHabitacion) {
		return ResponseEntity.ok(service.habitacionTieneReservasActivas(idHabitacion));
    }
}