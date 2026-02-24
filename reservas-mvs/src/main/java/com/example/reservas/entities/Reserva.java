package com.example.reservas.entities;


import com.example.common.enums.EstadoRegistro;
import com.example.common.enums.EstadoReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RESERVAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA")
    private Long id;

    @Column(name = "ID_HUESPED", nullable = false)
    private Long idHuesped;

    @Column(name = "ID_HABITACION", nullable = false)
    private Long idHabitacion;

    @Column(name = "FECHA_ENTRADA", nullable = false, length = 20)
    private String fechaEntrada;

    @Column(name = "FECHA_SALIDA", nullable = false, length = 20)
    private String fechaSalida;

    @Column(name = "ESTADO_RESERVA", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    @Column(name = "ESTADO_REGISTRO", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private EstadoRegistro estadoRegistro;
}


