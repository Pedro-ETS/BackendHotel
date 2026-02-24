package com.example.huespedes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.common.enums.EstadoRegistro;
import com.example.huespedes.entity.Huesped;

public interface HuespedRepository extends JpaRepository<Huesped, Long>{
	
	List<Huesped> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	
	Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
	
	

}
