package com.example.reservas.mappers;

import org.springframework.stereotype.Component;

import com.example.common.dto.HabitacionDTO;
import com.example.common.dto.HabitacionResponse;
import com.example.common.dto.HuespedDTO;
import com.example.common.dto.ReservaRequest;
import com.example.common.dto.ReservaResponse;
import com.example.common.dto.huesped.HuespedResponse;
import com.example.common.mapper.CommonMapper;
import com.example.reservas.entities.Reserva;

@Component
public class ReservaMapper implements CommonMapper<ReservaRequest, ReservaResponse, Reserva> {

    // ========================= ENTITY → RESPONSE (sin relaciones) =========================

    @Override
    public ReservaResponse entityToResponse(Reserva entity) {
        if (entity == null) return null;

        return new ReservaResponse(
                entity.getId(),
                null,
                null,
                entity.getFechaEntrada(),
                entity.getFechaSalida(),
                entity.getEstadoReserva().name(),
                entity.getEstadoRegistro().name()
        );
    }

    // ========================= ENTITY → RESPONSE (con relaciones) =========================

    public ReservaResponse entityToResponse(
            Reserva entity,
            HuespedDTO huesped,
            HabitacionDTO habitacion) {

        if (entity == null) return null;

        return new ReservaResponse(
                entity.getId(),
                huesped,
                habitacion,
                entity.getFechaEntrada(),
                entity.getFechaSalida(),
                entity.getEstadoReserva().name(),
                entity.getEstadoRegistro().name()
        );
    }

    // ========================= REQUEST → ENTITY =========================

    @Override
    public Reserva requestToEntity(ReservaRequest request) {
        if (request == null) return null;

        return Reserva.builder()
                .idHuesped(request.idHuesped())
                .idHabitacion(request.idHabitacion())
                .fechaEntrada(request.fechaEntrada())
                .fechaSalida(request.fechaSalida())
                .build();
    }

    // ========================= UPDATE ENTITY =========================

    @Override
    public Reserva updateEntityFromRequest(
            ReservaRequest request,
            Reserva entity) {

        if (request == null || entity == null) return entity;

        entity.setFechaEntrada(request.fechaEntrada());
        entity.setFechaSalida(request.fechaSalida());

        return entity;
    }

    // ========================= CLIENT CONVERSION =========================

    public HuespedDTO toHuespedDTO(HuespedResponse response) {

        if (response == null) return null;

        return new HuespedDTO(
                response.id(),
                String.join(" ",
                        response.nombre(),
                        response.apellidoPaterno(),
                        response.apellidoMaterno()
                ),
                response.email(),
                response.telefono(),
                response.documento(),
                response.nacionalidad()
        );
    }

    public HabitacionDTO toHabitacionDTO(HabitacionResponse response) {

        if (response == null) return null;

        return new HabitacionDTO(
                response.idHabitacion(),
                response.numero(),
                response.tipo(),
                response.precio(),
                response.capacidad(),
                response.estadoHabitacion()
        );
    }
}