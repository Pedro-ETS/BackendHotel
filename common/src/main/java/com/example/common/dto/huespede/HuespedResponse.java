package com.example.common.dto.huespede;

import com.example.common.enums.EstadoRegistro;

public record HuespedResponse(
		
		Long id,
		String nombre,
		String email,
		String telefono,
		String documento,
		String nacionalidad,
		EstadoRegistro estado
		
		) {

}
