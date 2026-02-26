package com.example.huespedes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.controller.CommonController;
import com.example.common.dto.huesped.HuespedRequest;
import com.example.common.dto.huesped.HuespedResponse;
import com.example.huespedes.service.HuespedService;


@RestController
public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService>{

	public HuespedController(HuespedService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/id-huesped/{id}")
	public ResponseEntity<HuespedResponse> obtenerHuespedPorIdSinEstado(@PathVariable Long id){
		return ResponseEntity.ok(service.obtenerHuespedPorIdSinEstado(id));
	}

}
