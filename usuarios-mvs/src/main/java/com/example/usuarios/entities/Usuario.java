package com.example.usuarios.entities;

import com.example.common.enums.EstadoRegistro;
import com.example.usuarios.enums.EstadoRol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIOS")
public class Usuario {

      
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;
    
    

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoRol rol;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoRegistro estadoRegistro;


}