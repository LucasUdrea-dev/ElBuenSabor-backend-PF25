package com.buenSabor.BackEnd.dto.seguridad.autenticacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationResponseDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;
    private String jwt;
}
