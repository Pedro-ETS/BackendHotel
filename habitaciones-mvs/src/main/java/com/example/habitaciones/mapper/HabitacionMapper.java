package com.example.habitaciones.mapper;

import com.example.common.dto.HabitacionResponse;
import com.example.common.enums.EstadoHabitacion;
import com.example.common.enums.EstadoRegistro;
import com.example.habitaciones.dtos.HabitacionRequest;
import com.example.habitaciones.entities.Habitacion;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper {

    public Habitacion requestToEntity(HabitacionRequest request) {
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(request.numero());           
        habitacion.setTipo(request.tipo());               
        habitacion.setPrecio(request.precio());           
        habitacion.setCapacidad(request.capacidad());     
        habitacion.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
        habitacion.setEstadoRegistro(EstadoRegistro.ACTIVO);
        return habitacion;
    }

    public HabitacionResponse entityToResponse(Habitacion entity) {
        return new HabitacionResponse(
            entity.getIdHabitacion(),
            entity.getNumero(),
            entity.getTipo(),
            entity.getPrecio(),
            entity.getCapacidad(),
            entity.getEstadoHabitacion().name(),
            entity.getEstadoRegistro()
        );
    }

    public void updateEntityFromRequest(HabitacionRequest request, Habitacion entity) {
        entity.setNumero(request.numero());              
        entity.setTipo(request.tipo());                   
        entity.setPrecio(request.precio());               
        entity.setCapacidad(request.capacidad());       
    }
}