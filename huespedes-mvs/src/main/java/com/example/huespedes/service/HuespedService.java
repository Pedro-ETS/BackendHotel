package com.example.huespedes.service;


import com.example.common.dto.huesped.HuespedRequest;
import com.example.common.dto.huesped.HuespedResponse;
import com.example.common.services.CrudServices;

public interface HuespedService extends CrudServices<HuespedRequest, HuespedResponse >{

	HuespedResponse obtenerHuespedPorIdSinEstado(Long id);

}
