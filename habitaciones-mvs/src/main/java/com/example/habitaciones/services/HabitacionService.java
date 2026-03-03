package com.example.habitaciones.services;

import com.example.common.dto.habitacion.HabitacionRequest;
import com.example.common.dto.habitacion.HabitacionResponse;
import com.example.common.services.CrudServices;
import java.util.List;

public interface HabitacionService extends CrudServices<HabitacionRequest, HabitacionResponse> {
    
    HabitacionResponse obtenerHabitacionPorIdSinEstado(Long id);
    
    HabitacionResponse cambiarEstadoHabitacion(Long idHabitacion, Long idEstado);
    
    HabitacionResponse liberarHabitacionDesdeReserva(Long idHabitacion, Long idEstado);
    
    List<HabitacionResponse> listarDisponibles();
    
    boolean validarDisponibilidad(Long idHabitacion);
    
 
}