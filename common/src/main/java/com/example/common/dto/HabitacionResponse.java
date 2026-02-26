package com.example.common.dto;

import java.math.BigDecimal;

public record HabitacionResponse(
    Long idHabitacion,
    Integer numero,
    String tipo,
    BigDecimal precio,
    Integer capacidad,
    String estadoHabitacion,
    String estadoRegistro
) {}