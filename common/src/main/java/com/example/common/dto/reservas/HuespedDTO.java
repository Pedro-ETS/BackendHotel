package com.example.common.dto.reservas;



public record HuespedDTO(
		
		Long id,
		String nombre,
		String email,
		String telefono,
        String documento,
        String nacionalidad

		) {

}
