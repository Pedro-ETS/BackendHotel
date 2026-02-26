package com.example.reservas.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.dto.ReservaRequest;
import com.example.common.dto.ReservaResponse;
import com.example.common.enums.EstadoRegistro;
import com.example.common.enums.EstadoReserva;
import com.example.reservas.entities.Reserva;
import com.example.reservas.mappers.ReservaMapper;
import com.example.reservas.repositories.ReservaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaServiceImpl  {

    
}