package com.example.common.dto.reservas;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservaRequest(

    @NotNull(message = "El ID del huésped es obligatorio")
    @Positive(message = "El ID del huésped debe ser mayor a 0")
    Long idHuesped,

    @NotNull(message = "El ID de la habitación es obligatorio")
    @Positive(message = "El ID de la habitación debe ser mayor a 0")
    Long idHabitacion,

    @NotNull(message = "La fecha de entrada es requerida")
	@FutureOrPresent(message = "La fecha de entrada debe ser futura")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	LocalDateTime fechaEntrada,

	@NotNull(message = "La fecha de salida es requerida")
	@FutureOrPresent(message = "La fecha de salida debe ser futura")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	LocalDateTime fechaSalida

) {}