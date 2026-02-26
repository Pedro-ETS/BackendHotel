package com.example.common.dto;

public record ReservaResponse(

    Long id,
    HuespedDTO huesped,
    HabitacionDTO habitacion,
    String fechaEntrada,
    String fechaSalida,
    String estadoReserva,
    String estadoRegistro

) 

{
    public record HuespedDTO(
        Long id,
        String nombreCompleto,
        String documento,
        String telefono,
        String email,
        String nacionalidad) {}

    public record HabitacionDTO(
        Long id,
        Integer numero,
        String tipo,
        String precio,
        Integer capacidad,
        String estadoHabitacion
    ) {}
}