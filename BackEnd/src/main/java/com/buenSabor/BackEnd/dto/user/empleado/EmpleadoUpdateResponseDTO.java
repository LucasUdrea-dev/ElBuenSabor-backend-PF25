package com.buenSabor.BackEnd.dto.user.empleado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoUpdateResponseDTO {

    private EmpleadoDTO empleadoUpdateDTO;
    private String token;
}
