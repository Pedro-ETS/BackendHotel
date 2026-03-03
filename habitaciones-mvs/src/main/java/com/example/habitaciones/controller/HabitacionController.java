package com.example.habitaciones.controller;

import com.example.common.dto.HabitacionResponse;
import com.example.habitaciones.dtos.HabitacionRequest;
import com.example.habitaciones.services.HabitacionService;
import com.example.common.controller.CommonController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
        
    } 
    public ResponseEntity<List<HabitacionResponse>> listar() {
        return super.listar();
    }


    public ResponseEntity<HabitacionResponse> registrar(@RequestBody HabitacionRequest request) {
        return super.registrar(request);
    }
    
   
    public ResponseEntity<Void> eliminar(Long id) {
        return super.eliminar(id);
    }
    
    
    @PatchMapping("/{id}/estado/{idEstado}")
    public ResponseEntity<HabitacionResponse> cambiarEstado(	
            @PathVariable Long id,
            @PathVariable Long idEstado) {
        return ResponseEntity.ok(service.cambiarEstadoHabitacion(id, idEstado));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/id-habitacion/{id}")
	public ResponseEntity<HabitacionResponse> obtenerHabitacionPorIdSinEstado	(@PathVariable Long id){
		return ResponseEntity.ok(service.obtenerHabitacionPorIdSinEstado(id));
	}
    
    //Endpoint que solo se usa para liberar la habitación al finalizar o cancelar una reserva.
    @PatchMapping("/{id}/estado-interno/{idEstado}")
    public ResponseEntity<HabitacionResponse> liberarHabitacionDesdeReserva(@PathVariable Long id,@PathVariable Long idEstado) {
        return ResponseEntity.ok(((HabitacionService) service).liberarHabitacionDesdeReserva(id, idEstado));
    }
    
}