package com.buenSabor.BackEnd.dto.seguridad.registro;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUsernameDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Email(message = "El nombre de usuario debe tener formato de correo")
    private String username;
}
