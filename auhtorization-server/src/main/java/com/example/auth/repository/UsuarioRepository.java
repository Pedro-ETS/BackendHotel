package com.example.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.auth.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	
	Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

}
