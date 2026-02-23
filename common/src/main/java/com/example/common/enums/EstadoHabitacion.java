package com.example.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoHabitacion {
	
	 DISPONIBLE(1L, "Disponible"),
	    OCUPADA(2L, "Ocupada"),
	    LIMPIEZA(3L, "En limpieza"),
	    MANTENIMIENTO(4L, "En mantenimiento");

	    private final Long codigo;
	    private final String descripcion;

	    public static EstadoHabitacion fromCodigo(Long codigo) {
	        for (EstadoHabitacion e : values()) {
	            if (e.codigo == codigo) {
	                return e;
	            }
	        }
	        throw new IllegalArgumentException("Código de estado de habitación no válido: " + codigo);
	    }

	    public static EstadoHabitacion fromDescripcion(String descripcion) {
	        for (EstadoHabitacion e : values()) {
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
