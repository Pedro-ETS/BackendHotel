package com.example.common.dto.habitacion;

import java.math.BigDecimal;

import com.example.common.enums.EstadoRegistro;

public record HabitacionResponse(
    Long idHabitacion,
    Integer numero,
    String tipo,
    BigDecimal precio,
    Integer capacidad,
    String estadoHabitacion,
    EstadoRegistro estadoRegistro
) {}