package com.example.habitaciones.controller;

import com.example.common.dto.HabitacionResponse;
import com.example.habitaciones.dtos.HabitacionRequest;
import com.example.habitaciones.services.HabitacionService;
import com.example.common.controller.CommonController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }
    
    
    @PatchMapping("/{id}/estado/{idEstado}")
    public ResponseEntity<HabitacionResponse> cambiarEstado(	
            @PathVariable Long id,
            @PathVariable Long idEstado) {
        return ResponseEntity.ok(service.cambiarEstadoHabitacion(id, idEstado));
    }
    
    @GetMapping("/id-habitacion/{id}")
	public ResponseEntity<HabitacionResponse> obtenerHabitacionPorIdSinEstado(@PathVariable Long id){
		return ResponseEntity.ok(service.obtenerHabitacionPorIdSinEstado(id));
	}
    
    //Endpoint que solo se usa para liberar la habitación al finalizar o cancelar una reserva.
    @PatchMapping("/{id}/estado-interno/{idEstado}")
    public ResponseEntity<HabitacionResponse> liberarHabitacionDesdeReserva(@PathVariable Long id,@PathVariable Long idEstado) {
        return ResponseEntity.ok(((HabitacionService) service).liberarHabitacionDesdeReserva(id, idEstado));
    }
    
}