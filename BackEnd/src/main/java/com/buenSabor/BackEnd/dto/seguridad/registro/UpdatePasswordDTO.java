package com.buenSabor.BackEnd.dto.seguridad.registro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDTO {
    protected String passwordActual;

    protected String passwordNuevo;
}
