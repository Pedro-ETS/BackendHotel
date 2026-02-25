package com.example.reservas.services;

import com.example.common.dto.ReservaRequest;
import com.example.common.dto.ReservaResponse;
import com.example.common.services.CrudServices;

public interface ReservaService extends CrudServices<ReservaRequest, ReservaResponse> {

    ReservaResponse cambiarEstado(Long idReserva, Integer idEstado);
}