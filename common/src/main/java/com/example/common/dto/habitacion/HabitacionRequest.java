package com.example.common.dto.habitacion;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record HabitacionRequest(
    @NotNull(message = "El número es obligatorio")
    @Positive(message = "El número debe ser positivo")
    Integer numero,
    
    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    String tipo,
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    BigDecimal precio,
    
    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es 1")
    Integer capacidad
) {}