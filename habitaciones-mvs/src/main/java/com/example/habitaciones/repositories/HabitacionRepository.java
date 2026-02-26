package com.example.habitaciones.repositories;

import com.example.common.enums.EstadoHabitacion;
import com.example.common.enums.EstadoRegistro;
import com.example.habitaciones.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByNumero(Integer numero);
    List<Habitacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    List<Habitacion> findByEstadoHabitacion(EstadoHabitacion estadoHabitacion);
    List<Habitacion> findByEstadoRegistroAndEstadoHabitacion(EstadoRegistro estadoRegistro, EstadoHabitacion estadoHabitacion);
    boolean existsByNumero(Integer numero);
}