package com.example.common.dto;

import java.time.LocalDateTime;

public record ReservaResponse(

		Long id,
	    HuespedDTO huesped,
	    HabitacionDTO habitacion,
	    LocalDateTime fechaEntrada,
	    LocalDateTime fechaSalida,
	    String estadoReserva,
	    String estadoRegistro
		){
}