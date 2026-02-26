package com.example.common.dto.huesped;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HuespedRequest(
		
		@NotBlank(message = "El nombre es obligatorio")
		@Size(min = 2, max = 50, message = "El nombre debe tener entre 2 a 50 caracteres")
		String nombre,
		
		@NotBlank(message = "El apellido paterno es obligatorio")
		@Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 a 50 caracteres")
		String apellidoPaterno,
		
		@NotBlank(message = "El Apellido Materno es obligatorio")
		@Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 a 50 caracteres")
		String apellidoMaterno,
		
		@NotBlank(message = "El email es obligatorio")
		@Size(max = 100, message = "El email solo puede contener 100 caracteres")
        @Email(message = "El email debe tener un formato valido (ejemplo@correo.com)")
		String email,
		
		@NotBlank(message = "El telefono es obligatorio")
        @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe contener solo 10 digitos")
        String telefono,
        
        @NotBlank(message = "El documento es obligatorio")
		@Pattern(regexp = "^[A-Za-z0-9-]{5,20}$",
			    message = "El documento debe contener entre 5 y 20 caracteres, solo letras, números o guiones")		
		String documento,
		
		@NotBlank(message = "La nacionalidad es obligatoria")
		@Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,50}$",
			    message = "La nacionalidad solo puede contener letras y espacios")		
		String nacionalidad
		
		
		) {

}
