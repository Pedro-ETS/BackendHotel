package com.example.reservas.mappers;

import org.springframework.stereotype.Component;

import com.example.common.dto.ReservaRequest;
import com.example.common.dto.ReservaResponse;
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
            ReservaResponse.HuespedDTO huesped,
            ReservaResponse.HabitacionDTO habitacion) {

        return new ReservaResponse(
                entity.getId(),
                huesped,
                habitacion,
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