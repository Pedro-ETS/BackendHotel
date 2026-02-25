package com.example.huespedes.mapper;

import org.springframework.stereotype.Component;


import com.example.common.dto.huesped.HuespedRequest;
import com.example.common.dto.huesped.HuespedResponse;
import com.example.common.enums.EstadoRegistro;
import com.example.common.mapper.CommonMapper;
import com.example.huespedes.entity.Huesped;

import lombok.Builder;


@Component
@Builder
public class HuespedMapper implements CommonMapper <HuespedRequest, HuespedResponse, Huesped>{

	@Override
	public HuespedResponse entityToResponse(Huesped entity) {
		if(entity == null) return null;
		
		
		return new HuespedResponse(
				entity.getId(),
				String.join(" ", 
						entity.getNombre(),
						entity.getApellidoPaterno(),
						entity.getApellidoMaterno()),
				entity.getEmail(),
				entity.getTelefono(),
				entity.getDocumento(),
				entity.getNacionalidad(),
				entity.getEstadoRegistro());
	}

	@Override
	public Huesped requestToEntity(HuespedRequest request) {
		if(request == null) return null;
		
		return Huesped.builder()
				.nombre(request.nombre())
				.apellidoPaterno(request.apellidoPaterno())
				.apellidoMaterno(request.apellidoMaterno())
				.email(request.email())
				.telefono(request.telefono())
				.documento(request.documento())
				.nacionalidad(request.nacionalidad())
				.estadoRegistro(EstadoRegistro.ACTIVO).build();
	}

	@Override
	public Huesped updateEntityFromRequest(HuespedRequest request, Huesped entity) {
		if(request == null || entity == null) return entity;
		
		entity.setNombre(request.nombre());
		entity.setApellidoPaterno(request.apellidoPaterno());
		entity.setApellidoMaterno(request.apellidoMaterno());
		entity.setEmail(request.email());
		entity.setTelefono(request.telefono());
		entity.setDocumento(request.documento());
		entity.setNacionalidad(request.nacionalidad());
		
		return entity;
	}

}
