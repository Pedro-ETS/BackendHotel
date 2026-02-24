package com.example.huespedes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.common.dto.huespede.HuespedRequest;
import com.example.common.dto.huespede.HuespedResponse;
import com.example.huespedes.mapper.HuespedMapper;
import com.example.huespedes.repository.HuespedRepository;

import jakarta.transaction.Transactional;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HuespedResponse obtenerPorID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HuespedResponse registrar(HuespedRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HuespedResponse actualizar(HuespedRequest request, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id) {
		// TODO Auto-generated method stub
		
	}

}
