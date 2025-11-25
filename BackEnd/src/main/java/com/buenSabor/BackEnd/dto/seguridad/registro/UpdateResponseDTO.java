package com.buenSabor.BackEnd.dto.seguridad.registro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateResponseDTO {
    private String mensaje;
    private String token;
}
