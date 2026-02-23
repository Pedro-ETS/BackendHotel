package com.example.common.enums;import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoReserva {
	
	PENDIENTE(1L, "Pendiente"),
    CONFIRMADA(2L, "Confirmada"),
    EN_CURSO(3L, "En curso"),
    FINALIZADA(4L, "Finalizada"),
    CANCELADA(5L, "Cancelada"),
    NO_SHOW(6L, "No se presentó");

    private final Long codigo;
    private final String descripcion;

    public static EstadoReserva fromCodigo(Long codigo) {
        for (EstadoReserva e : values()) {
        	if (e.codigo.equals(codigo)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Código de estado de reserva no válido: " + codigo);
    }

    public static EstadoReserva fromDescripcion(String descripcion) {
        for (EstadoReserva e : values()) {
            String desc = quitarAcentos(e.descripcion);
            if (desc.equalsIgnoreCase(descripcion)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Descripción no válida: " + descripcion);
    }

    private static String quitarAcentos(String s) {
        return s
                .replace("á", "a").replace("Á", "A")
                .replace("é", "e").replace("É", "E")
                .replace("í", "i").replace("Í", "I")
                .replace("ó", "o").replace("Ó", "O")
                .replace("ú", "u").replace("Ú", "U");
    }
}


