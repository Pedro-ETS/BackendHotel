package com.example.reservas.mappers;

import org.springframework.stereotype.Component;

import com.example.common.dto.HabitacionResponse;
import com.example.common.dto.ReservaRequest;
import com.example.common.dto.ReservaResponse;
import com.example.common.dto.huesped.HuespedResponse;
import com.example.common.enums.EstadoRegistro;
import com.example.common.enums.EstadoReserva;
import com.example.reservas.entities.Reserva;

@Component
public class ReservaMapper {

    public Reserva requestToEntity(ReservaRequest request) {
        return Reserva.builder()
                .idHuesped(request.idHuesped())
                .idHabitacion(request.idHabitacion())
                .fechaEntrada(request.fechaEntrada())
                .fechaSalida(request.fechaSalida())
                .estadoReserva(EstadoReserva.CONFIRMADA)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    public ReservaResponse entityToResponse(Reserva entity,
    		HuespedResponse huesped,
    		HabitacionResponse habitacion) {

    	 ReservaResponse.HuespedDTO huespedDTO = new ReservaResponse.HuespedDTO(
                 huesped.id(),
                 huesped.nombre(),
                 huesped.email(),
                 huesped.telefono(),
                 huesped.documento(),
                 huesped.nacionalidad()
         );
    	 
    	 ReservaResponse.HabitacionDTO habitacionDTO = new ReservaResponse.HabitacionDTO(
                 habitacion.idHabitacion(),
                 habitacion.numero(),
                 habitacion.tipo(),
                 habitacion.precio().toString(),
                 habitacion.capacidad(),
                 habitacion.estadoHabitacion()
         );
    	 
    	 return new ReservaResponse(
                 entity.getId(),
                 huespedDTO,
                 habitacionDTO,
                 entity.getFechaEntrada(),
                 entity.getFechaSalida(),
                 entity.getEstadoReserva().getDescripcion(),
                 entity.getEstadoRegistro().name()
         );
    }

    public void updateEntity(ReservaRequest request, Reserva entity) {
        entity.setFechaEntrada(request.fechaEntrada());
        entity.setFechaSalida(request.fechaSalida());
    }
}