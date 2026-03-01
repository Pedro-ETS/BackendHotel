package com.example.reservas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.common.enums.EstadoRegistro;
import com.example.common.enums.EstadoReserva;
import com.example.reservas.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    Optional<Reserva> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    boolean existsByIdHuespedAndEstadoReservaInAndEstadoRegistro(Long idHuesped,List<EstadoReserva> estados,EstadoRegistro estadoRegistro);

    boolean existsByIdHabitacionAndEstadoReservaInAndEstadoRegistro(Long idHabitacion,List<EstadoReserva> estados,EstadoRegistro estadoRegistro);

	Object existsByIdHabitacionAndEstadoReservaIn(Long idHabitacion, List<EstadoReserva> of);
}