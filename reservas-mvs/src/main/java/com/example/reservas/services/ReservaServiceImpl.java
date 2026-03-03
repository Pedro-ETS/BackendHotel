package com.example.reservas.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.clients.HabitacionClient;
import com.example.common.clients.HuespedClient;
import com.example.common.dto.habitacion.HabitacionResponse;
import com.example.common.dto.huesped.HuespedResponse;
import com.example.common.dto.reservas.HabitacionDTO;
import com.example.common.dto.reservas.HuespedDTO;
import com.example.common.dto.reservas.ReservaRequest;
import com.example.common.dto.reservas.ReservaResponse;
import com.example.common.enums.EstadoHabitacion;
import com.example.common.enums.EstadoRegistro;
import com.example.common.enums.EstadoReserva;
import com.example.common.exceptions.EntidadRelacionadaException;
import com.example.reservas.entities.Reserva;
import com.example.reservas.mappers.ReservaMapper;
import com.example.reservas.repositories.ReservaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final HuespedClient huespedClient;
    private final HabitacionClient habitacionClient;



    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponse> listar() {

        return reservaRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream()
                .map(reserva -> {

                    HuespedResponse huespedResponse =
                            huespedClient.obtenerHuespedPorId(reserva.getIdHuesped());

                    HuespedDTO huespedDTO =
                            reservaMapper.toHuespedDTO(huespedResponse);

                    HabitacionResponse habitacionResponse =
                            habitacionClient.obtenerHabitacionPorId(reserva.getIdHabitacion());

                    HabitacionDTO habitacionDTO =
                            reservaMapper.toHabitacionDTO(habitacionResponse);

                    return reservaMapper.entityToResponse(reserva,huespedDTO,habitacionDTO);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponse obtenerPorID(Long id) {

        Reserva reserva = getReservaActivaOrThrow(id);

        HuespedDTO huespedDTO =
                reservaMapper.toHuespedDTO(huespedClient.obtenerHuespedPorId(reserva.getIdHuesped()));

        HabitacionDTO habitacionDTO =
                reservaMapper.toHabitacionDTO(habitacionClient.obtenerHabitacionPorId(reserva.getIdHabitacion()));

        return reservaMapper.entityToResponse(reserva,huespedDTO,habitacionDTO);
    }

    @Override
    @Transactional
    public ReservaResponse registrar(ReservaRequest request) {

        validarFechas(request.fechaEntrada(), request.fechaSalida());

        HuespedResponse huespedResponse =
                huespedClient.obtenerHuespedPorId(request.idHuesped());

        if (huespedResponse.estado() != EstadoRegistro.ACTIVO) {

            throw new EntidadRelacionadaException("El huésped no está activo");
        }

        HabitacionResponse habitacionResponse =
                habitacionClient.obtenerHabitacionPorId(request.idHabitacion());

        if (habitacionResponse.estadoRegistro() != EstadoRegistro.ACTIVO) {

            throw new EntidadRelacionadaException("La habitación no está activa");
        }

        if (!EstadoHabitacion.DISPONIBLE.name()
                .equals(habitacionResponse.estadoHabitacion())) {

            throw new EntidadRelacionadaException("La habitación no está disponible");
        }

        Reserva reserva = reservaMapper.requestToEntity(request);
        reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
        reserva.setEstadoRegistro(EstadoRegistro.ACTIVO);

        Reserva guardada = reservaRepository.save(reserva);

        habitacionClient.cambiarEstadoHabitacion(
                request.idHabitacion(),EstadoHabitacion.OCUPADA.getCodigo());

        return obtenerPorID(guardada.getId());
    }
    
    @Override
    public ReservaResponse actualizar(ReservaRequest request, Long id) {

        Reserva reserva = getReservaActivaOrThrow(id);

        if (reserva.getEstadoReserva() == EstadoReserva.FINALIZADA ||
            reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {

            throw new EntidadRelacionadaException("No se puede modificar una reserva finalizada o cancelada");
        }

        if (!reserva.getIdHuesped().equals(request.idHuesped())) {
            throw new EntidadRelacionadaException("No se puede cambiar el huésped de la reserva");
        }

        if (!reserva.getIdHabitacion().equals(request.idHabitacion())) {
            throw new EntidadRelacionadaException("No se puede cambiar la habitación de la reserva");
        }

        if (reserva.getEstadoReserva() == EstadoReserva.EN_CURSO &&
            !reserva.getFechaEntrada().equals(request.fechaEntrada())) {

            throw new EntidadRelacionadaException("No se puede modificar la fecha de entrada después del check-in");
        }

        validarFechas(request.fechaEntrada(), request.fechaSalida());

        reservaMapper.updateEntityFromRequest(request, reserva);

        Reserva actualizada = reservaRepository.save(reserva);

        return obtenerPorID(actualizada.getId());
    }


    @Override
    public ReservaResponse cambiarEstado(Long idReserva, Integer idEstado) {

        Reserva reserva = getReservaActivaOrThrow(idReserva);

        EstadoReserva estadoNuevo =EstadoReserva.fromCodigo(idEstado.longValue());

        validarTransicion(reserva.getEstadoReserva(), estadoNuevo);

        reserva.setEstadoReserva(estadoNuevo);
        

        if (estadoNuevo == EstadoReserva.FINALIZADA ||
            estadoNuevo == EstadoReserva.CANCELADA) {

        	habitacionClient.liberarHabitacionDesdeReserva(
                    reserva.getIdHabitacion(),EstadoHabitacion.DISPONIBLE.getCodigo());
        }

        reservaRepository.save(reserva);
        return obtenerPorID(reserva.getId());
    }
    

    @Override
    public void eliminar(Long id) {

        Reserva reserva = getReservaActivaOrThrow(id);

        if (reserva.getEstadoReserva() == EstadoReserva.EN_CURSO) {

            throw new EntidadRelacionadaException("No se puede eliminar una reserva que se encuentra en curso");
        }

        if (reserva.getEstadoReserva() == EstadoReserva.CONFIRMADA) {

            habitacionClient.liberarHabitacionDesdeReserva(
                    reserva.getIdHabitacion(),EstadoHabitacion.DISPONIBLE.getCodigo());
        }

        reserva.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        reservaRepository.save(reserva);
    }


    @Override
    @Transactional(readOnly = true)
    public Boolean huespedTieneReservasActivas(Long idHuesped) {

        return reservaRepository
                .existsByIdHuespedAndEstadoReservaInAndEstadoRegistro(
                        idHuesped,
                        List.of(EstadoReserva.CONFIRMADA, EstadoReserva.EN_CURSO),
                        EstadoRegistro.ACTIVO);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean habitacionTieneReservasActivas(Long idHabitacion) {
        return reservaRepository
                .existsByIdHabitacionAndEstadoReservaInAndEstadoRegistro(
                        idHabitacion,
                        List.of(EstadoReserva.CONFIRMADA, EstadoReserva.EN_CURSO),
                        EstadoRegistro.ACTIVO);
    }

    private Reserva getReservaActivaOrThrow(Long id) {
        return reservaRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() ->new NoSuchElementException("Reserva no encontrada"));
    }

    private void validarFechas(LocalDateTime entrada, LocalDateTime salida) {

        if (!entrada.isBefore(salida)) {

            throw new IllegalArgumentException("La fecha de entrada debe ser anterior a la fecha de salida");
        }
    }

    private void validarTransicion(EstadoReserva actual, EstadoReserva nuevo) {

        boolean valida = switch (actual) {

            case CONFIRMADA ->
                    nuevo == EstadoReserva.EN_CURSO ||
                    nuevo == EstadoReserva.CANCELADA;

            case EN_CURSO ->
                    nuevo == EstadoReserva.FINALIZADA;

            case FINALIZADA, CANCELADA -> false;
        };

        if (!valida) {
            throw new EntidadRelacionadaException("Transición de estado inválida");
        }
    }
}