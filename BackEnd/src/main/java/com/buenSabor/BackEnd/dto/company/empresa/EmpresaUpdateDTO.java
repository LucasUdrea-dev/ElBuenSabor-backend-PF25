package com.buenSabor.BackEnd.dto.company.empresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaUpdateDTO {

    private String nombre;

    private String razonSocial;

    private String cuil;

    private Boolean existe;
}