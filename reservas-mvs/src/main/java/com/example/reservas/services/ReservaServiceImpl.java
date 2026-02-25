package com.example.reservas.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.dto.ReservaRequest;
import com.example.common.dto.ReservaResponse;
import com.example.common.enums.EstadoRegistro;
import com.example.common.enums.EstadoReserva;
import com.example.reservas.entities.Reserva;
import com.example.reservas.mappers.ReservaMapper;
import com.example.reservas.repositories.ReservaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    // TODO: agregar HuespedClient y HabitacionClient cuando estén listos

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponse> listar() {
        log.info("Listando todas las reservas activas");
        return reservaRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream()
                .map(reserva -> reservaMapper.entityToResponse(
                        reserva,
                        null, // TODO: obtenerHuesped(reserva.getIdHuesped())
                        null  // TODO: obtenerHabitacion(reserva.getIdHabitacion())
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponse obtenerPorID(Long id) {
        log.info("Obteniendo reserva con id: {}", id);
        Reserva reserva = getReservaActivaOrThrow(id);
        return reservaMapper.entityToResponse(
                reserva,
                null, // TODO: obtenerHuesped(reserva.getIdHuesped())
                null  // TODO: obtenerHabitacion(reserva.getIdHabitacion())
        );
    }

    @Override
    @Transactional
    public ReservaResponse registrar(ReservaRequest request) {
        log.info("Registrando nueva reserva");

        // TODO: Validar que el huésped existe y está ACTIVO
        // HuespedResponse huesped = huespedClient.obtenerHuespedPorId(request.idHuesped());

        // TODO: Validar que la habitación existe, está ACTIVA y DISPONIBLE
        // HabitacionResponse habitacion = habitacionClient.obtenerHabitacionPorId(request.idHabitacion());
        // if (!"DISPONIBLE".equals(habitacion.estado())) {
        //     throw new IllegalStateException("La habitación no está disponible");
        // }

        // Validar fechas
        validarFechas(request.fechaEntrada(), request.fechaSalida());

        // Crear reserva en estado CONFIRMADA
        Reserva reserva = reservaMapper.requestToEntity(request);
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // TODO: Cambiar habitación a OCUPADA
        // habitacionClient.cambiarEstadoHabitacion(request.idHabitacion(), 2);

        log.info("Reserva registrada exitosamente con id: {}", reservaGuardada.getId());

        return reservaMapper.entityToResponse(
                reservaGuardada,
                null, // TODO: huesped
                null  // TODO: habitacion
        );
    }

    @Override
    @Transactional
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        log.info("Actualizando reserva con id: {}", id);

        Reserva reserva = getReservaActivaOrThrow(id);

        // Validar que no esté FINALIZADA o CANCELADA
        if (reserva.getEstadoReserva() == EstadoReserva.FINALIZADA ||
            reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new IllegalStateException(
                    "No se puede modificar una reserva FINALIZADA o CANCELADA");
        }

        // Si está EN_CURSO solo se puede modificar fecha de salida
        if (reserva.getEstadoReserva() == EstadoReserva.EN_CURSO) {
            if (!reserva.getFechaEntrada().equals(request.fechaEntrada())) {
                throw new IllegalStateException(
                        "No se puede modificar la fecha de entrada después del check-in");
            }
        }

        // Validar fechas
        validarFechas(request.fechaEntrada(), request.fechaSalida());

        reservaMapper.updateEntity(request, reserva);
        Reserva reservaActualizada = reservaRepository.save(reserva);

        log.info("Reserva actualizada exitosamente con id: {}", id);

        return reservaMapper.entityToResponse(
                reservaActualizada,
                null, // TODO: obtenerHuesped
                null  // TODO: obtenerHabitacion
        );
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando reserva con id: {}", id);

        Reserva reserva = getReservaActivaOrThrow(id);

        // No se puede eliminar si está EN_CURSO o FINALIZADA
        if (reserva.getEstadoReserva() == EstadoReserva.EN_CURSO ||
            reserva.getEstadoReserva() == EstadoReserva.FINALIZADA) {
            throw new IllegalStateException(
                    "No se puede eliminar una reserva EN_CURSO o FINALIZADA");
        }

        reserva.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        reservaRepository.save(reserva);

        log.info("Reserva eliminada exitosamente (lógico) con id: {}", id);
    }

    @Override
    @Transactional
    public ReservaResponse cambiarEstado(Long idReserva, Integer idEstado) {
        log.info("Cambiando estado de reserva {} a {}", idReserva, idEstado);

        Reserva reserva = getReservaActivaOrThrow(idReserva);
        EstadoReserva estadoActual = reserva.getEstadoReserva();
        EstadoReserva estadoNuevo = EstadoReserva.fromCodigo(idEstado.longValue());

        // Validar transiciones
        validarTransicion(estadoActual, estadoNuevo);

        reserva.setEstadoReserva(estadoNuevo);
        reservaRepository.save(reserva);

        // Actualizar habitación según el nuevo estado
        // TODO: cuando HabitacionClient esté listo
        // if (estadoNuevo == EstadoReserva.FINALIZADA || estadoNuevo == EstadoReserva.CANCELADA) {
        //     habitacionClient.cambiarEstadoHabitacion(reserva.getIdHabitacion(), 1); // DISPONIBLE
        // }

        log.info("Estado de reserva {} cambiado a {}", idReserva, estadoNuevo.getDescripcion());

        return reservaMapper.entityToResponse(
                reserva,
                null, // TODO: obtenerHuesped
                null  // TODO: obtenerHabitacion
        );
    }

    // ═══════════════════════════════════════════════════════════
    // MÉTODOS PRIVADOS
    // ═══════════════════════════════════════════════════════════

    private Reserva getReservaActivaOrThrow(Long id) {
        return reservaRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new NoSuchElementException(
                        "Reserva activa no encontrada con id: " + id));
    }

    private void validarFechas(String fechaEntrada, String fechaSalida) {
        // Validar formato dd/MM/yyyy y que fechaEntrada < fechaSalida
        // Como las fechas son String comparamos lexicográficamente en formato yyyy/MM/dd
        // Por eso convertimos antes de comparar
        String[] entrada = fechaEntrada.split("/");
        String[] salida = fechaSalida.split("/");

        String entradaComparable = entrada[2] + entrada[1] + entrada[0];
        String salidaComparable = salida[2] + salida[1] + salida[0];

        if (entradaComparable.compareTo(salidaComparable) >= 0) {
            throw new IllegalArgumentException("La fecha de entrada debe ser anterior a la fecha de salida");
        }
    }

    private void validarTransicion(EstadoReserva actual, EstadoReserva nuevo) {
        boolean valida = switch (actual) {
            case CONFIRMADA -> nuevo == EstadoReserva.EN_CURSO || 
                               nuevo == EstadoReserva.CANCELADA;
            case EN_CURSO -> nuevo == EstadoReserva.FINALIZADA;
            case FINALIZADA, CANCELADA -> false;
        };

        if (!valida) {
            throw new IllegalStateException(
                    "No se puede cambiar de " + actual.getDescripcion() +
                    " a " + nuevo.getDescripcion());
        }
    }
}