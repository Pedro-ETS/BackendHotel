package com.example.huespedes.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;


import com.example.common.dto.huesped.HuespedRequest;
import com.example.common.dto.huesped.HuespedResponse;
import com.example.common.enums.EstadoRegistro;
import com.example.common.exceptions.EntidadRelacionadaException;
import com.example.huespedes.entity.Huesped;
import com.example.huespedes.mapper.HuespedMapper;
import com.example.huespedes.repository.HuespedRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HuespedServiceImpl implements HuespedService{
	
	private final HuespedRepository huespedRepository;
	private final HuespedMapper huespedMapper;

	@Override
	public List<HuespedResponse> listar() {
		log.info("Listado de huespedes");
		return huespedRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
				.stream()
				.map(huespedMapper :: entityToResponse)
				.toList();

	}

	@Override
	public HuespedResponse obtenerPorID(Long id) {
		  
		return huespedMapper.entityToResponse(getHuespedOrThrow(id));
	}
	
    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerHuespedPorIdSinEstado(Long id) {

        log.info("Buscando huesped con el id: {}", id);

        return huespedMapper.entityToResponse(
                huespedRepository.findById(id)
                        .orElseThrow(() ->
                                new NoSuchElementException(
                                        "Huesped no encontrado con el id: " + id))
        );
    }

	@Override
	public HuespedResponse registrar(HuespedRequest request) {
		log.info("Registrando huesped nuevo: {}", request.nombre());

		
		validarEmailUnico(request.email());
		
		validarTelefonoUnico(request.telefono());
		
		validarDocumentoUnico(request.documento());
		
		Huesped huesped= huespedMapper.requestToEntity(request);

		huesped.setEstadoRegistro(EstadoRegistro.ACTIVO);

	    Huesped guardado = huespedRepository.save(huesped);
	    return huespedMapper.entityToResponse(guardado);
	}

	@Override
	public HuespedResponse actualizar(HuespedRequest request, Long id) {

		Huesped huesped = getHuespedOrThrow(id);
		 
	    log.info("Actualizar huesped: {}", huesped.getNombre());
	     
	    validarCambiosUnicos(request, id);
	     
	    huespedMapper.updateEntityFromRequest(request, huesped);
	    
	    Huesped actualizado = huespedRepository.save(huesped);
	    return huespedMapper.entityToResponse(actualizado);
	
	}

	@Override
	public void eliminar(Long id) {
		log.info("Eliminando huesped con id: {}", id);
		
		Huesped huesped = getHuespedOrThrow(id);
		
		//citaClient.pacienteTieneConsultasConfirmadasOEnCurso(id);  Fakta cambiarlo con reservas para que valide que no tenga una reservacion
		
		log.info("Eliminando huesped con id: {}", id);
		
		huesped.setEstadoRegistro(EstadoRegistro.ELIMINADO);
		huespedRepository.save(huesped);
		log.info("El huesped con id: {} ha sido marcado como eliminado", id);
		
	}
	//-----------------------------------Metodos privados------------------------------------------------
	
	   private Huesped getHuespedOrThrow(Long id) {

	        log.info("Buscando huesped activo con id: {}", id);

	        return huespedRepository
	                .findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
	                .orElseThrow(() ->
	                        new NoSuchElementException(
	                                "Huesped activo no encontrado con el id: " + id));
	    }
	   
	   private void validarEmailUnico(String email) {
			if(huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistro(email, EstadoRegistro.ACTIVO)) {
				throw new EntidadRelacionadaException("Ya existe un huesped registrado con el email: " + email);
			}
		}
		
		private void validarTelefonoUnico(String telefono) {
	    	if(huespedRepository.existsByTelefonoIgnoreCaseAndEstadoRegistro(telefono,EstadoRegistro.ACTIVO)) {
	    		throw new EntidadRelacionadaException("Ya existe un huesped registrado con el telefono:"+ telefono);
	    	}
	    }
		
		private void validarDocumentoUnico(String documento) {
	    	if(huespedRepository.existsByDocumentoIgnoreCaseAndEstadoRegistro(documento,EstadoRegistro.ACTIVO)) {
	    		throw new EntidadRelacionadaException("Ya existe un huesped registrado con el documento:"+ documento);
	    	}
	    }
		
		private void validarCambiosUnicos(HuespedRequest request, Long id) {
			
			if(huespedRepository.existsByEmailIgnoreCaseAndIdNotAndEstadoRegistro(request.email(), id, EstadoRegistro.ACTIVO)) {
				
				throw new EntidadRelacionadaException("Ya existe un huesped registrado con el email: " + request.email());
			}	
			if(huespedRepository.existsByTelefonoIgnoreCaseAndIdNotAndEstadoRegistro(request.telefono(), id, EstadoRegistro.ACTIVO)) {
				
				throw new EntidadRelacionadaException("Ya existe un huesped registrado con el telefono: " + request.telefono());
			}
			
			if(huespedRepository.existsByDocumentoIgnoreCaseAndIdNotAndEstadoRegistro(request.documento(), id, EstadoRegistro.ACTIVO)) {
				
				throw new EntidadRelacionadaException("Ya existe un huesped registrado con el documento: " + request.documento());
			}
		}
	   

}
