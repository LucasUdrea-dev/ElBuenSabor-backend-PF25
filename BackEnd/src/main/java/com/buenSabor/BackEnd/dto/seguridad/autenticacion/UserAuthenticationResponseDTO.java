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
    private String jwt;
}
