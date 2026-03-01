package com.example.habitaciones.services;

import com.example.common.clients.ReservaClient;
import com.example.common.dto.HabitacionResponse;
import com.example.common.enums.EstadoHabitacion;
import com.example.common.enums.EstadoRegistro;
import com.example.common.exceptions.EntidadRelacionadaException;
import com.example.habitaciones.dtos.HabitacionRequest;
import com.example.habitaciones.entities.Habitacion;
import com.example.habitaciones.mapper.HabitacionMapper;
import com.example.habitaciones.repositories.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;
    private final ReservaClient reservaClient;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        log.info("Listando habitaciones activas");
        return habitacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream()
                .map(habitacionMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorID(Long id) {
        log.info("Buscando habitación activa con ID: {}", id);
        Habitacion habitacion = habitacionRepository.findById(id)
                .filter(h -> h.getEstadoRegistro() == EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new NoSuchElementException("No se encontró habitación activa con ID: " + id));
        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerHabitacionPorIdSinEstado(Long id) {
        log.info("Buscando habitación sin estado con ID: {}", id);
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró habitación con ID: " + id));
        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando nueva habitación: {}", request.numero());

        validarNumeroUnico(request.numero(), null);

        Habitacion habitacion = habitacionMapper.requestToEntity(request);
        habitacion = habitacionRepository.save(habitacion);
        log.info("Habitación registrada con ID: {}", habitacion.getIdHabitacion());

        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        log.info("Actualizando habitación con ID: {}", id);

        Habitacion habitacion = habitacionRepository.findById(id)
                .filter(h -> h.getEstadoRegistro() == EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new NoSuchElementException("No se encontró habitación activa con ID: " + id));

        validarNumeroUnico(request.numero(), id);

        habitacionMapper.updateEntityFromRequest(request, habitacion);
        habitacion = habitacionRepository.save(habitacion);
        log.info("Habitación actualizada con ID: {}", id);

        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    public HabitacionResponse cambiarEstadoHabitacion(Long idHabitacion, Long idEstado) {
        log.info("Cambiando estado de habitación ID: {} a código: {}", idHabitacion, idEstado);

        Habitacion habitacion = habitacionRepository.findById(idHabitacion)
                .filter(h -> h.getEstadoRegistro() == EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new NoSuchElementException("No se encontró habitación activa con ID: " + idHabitacion));

        EstadoHabitacion nuevoEstado = EstadoHabitacion.fromCodigo(idEstado);
        
        if (habitacion.getEstadoHabitacion() == EstadoHabitacion.OCUPADA
                && nuevoEstado == EstadoHabitacion.DISPONIBLE) {

            throw new EntidadRelacionadaException(
                    "No se puede cambiar la habitación a DISPONIBLE porque actualmente está OCUPADA");
        }
        
        habitacion.setEstadoHabitacion(nuevoEstado);
        habitacion = habitacionRepository.save(habitacion);

        log.info("Estado de habitación {} cambiado a: {}", idHabitacion, nuevoEstado.getDescripcion());
        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    public HabitacionResponse actualizarEstadoInterno(Long idHabitacion, Long idEstado) {
        log.info("Actualización interna de estado de habitación {}", idHabitacion);

        Habitacion habitacion = habitacionRepository.findById(idHabitacion)
                .orElseThrow(() -> new NoSuchElementException("No se encontró habitación con ID: " + idHabitacion));

        EstadoHabitacion nuevoEstado = EstadoHabitacion.fromCodigo(idEstado);
        habitacion.setEstadoHabitacion(nuevoEstado);
        habitacion = habitacionRepository.save(habitacion);

        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listarDisponibles() {
        log.info("Listando habitaciones disponibles");
        return habitacionRepository.findByEstadoRegistroAndEstadoHabitacion(
                        EstadoRegistro.ACTIVO,
                        EstadoHabitacion.DISPONIBLE)
                .stream()
                .map(habitacionMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validarDisponibilidad(Long idHabitacion) {
        log.info("Validando disponibilidad de habitación ID: {}", idHabitacion);
        return habitacionRepository.findById(idHabitacion)
                .map(h -> h.getEstadoRegistro() == EstadoRegistro.ACTIVO &&
                          h.getEstadoHabitacion() == EstadoHabitacion.DISPONIBLE)
                .orElse(false);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando (lógico) habitación con ID: {}", id);

        Habitacion habitacion = habitacionRepository.findById(id)
                .filter(h -> h.getEstadoRegistro() == EstadoRegistro.ACTIVO)
                .orElseThrow(() ->
                        new NoSuchElementException("No se encontró habitación activa con ID: " + id)
                );
        if (habitacion.getEstadoHabitacion() == EstadoHabitacion.OCUPADA) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar la habitación porque actualmente está OCUPADA"
            );
        }
        if (tieneReservasActivas(id)) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar la habitación porque tiene reservas activas"
            );
        }

        habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        habitacionRepository.save(habitacion);

        log.info("Habitación con ID: {} marcada como ELIMINADO", id);
    }

    private void validarNumeroUnico(Integer numero, Long idExcluir) {
        if (idExcluir == null) {
            if (habitacionRepository.existsByNumeroAndEstadoRegistro(
                    numero,
                    EstadoRegistro.ACTIVO)) {

                throw new EntidadRelacionadaException(
                        "Ya existe una habitación con el número: " + numero);
            }

        } 
        else {
            if (habitacionRepository.existsByNumeroAndIdHabitacionNotAndEstadoRegistro(
                    numero,
                    idExcluir,
                    EstadoRegistro.ACTIVO)) {

                throw new EntidadRelacionadaException(
                        "Ya existe otra habitación con el número: " + numero);
            }
        }
    }
    
    private boolean tieneReservasActivas(Long idHabitacion) {
        return Boolean.TRUE.equals(
            reservaClient.habitacionTieneReservasActivas(idHabitacion)
        );
    }
    
}