package com.example.common.dto.huesped;

import com.example.common.enums.EstadoRegistro;

public record HuespedResponse(
		
		Long id,
		String nombre,
		String apellidoPaterno,
		String apellidoMaterno,
		String email,
		String telefono,
		String documento,
		String nacionalidad,
		EstadoRegistro estado
		
		) {

}