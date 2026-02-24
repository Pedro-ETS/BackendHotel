package com.example.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ReservaRequest(

    @NotNull(message = "El ID del huésped es obligatorio")
    @Positive(message = "El ID del huésped debe ser mayor a 0")
    Long idHuesped,

    @NotNull(message = "El ID de la habitación es obligatorio")
    @Positive(message = "El ID de la habitación debe ser mayor a 0")
    Long idHabitacion,

    @NotBlank(message = "La fecha de entrada es obligatoria")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "La fecha debe tener el formato dd/MM/yyyy")
    String fechaEntrada,

    @NotBlank(message = "La fecha de salida es obligatoria")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "La fecha debe tener el formato dd/MM/yyyy")
    String fechaSalida

) {}