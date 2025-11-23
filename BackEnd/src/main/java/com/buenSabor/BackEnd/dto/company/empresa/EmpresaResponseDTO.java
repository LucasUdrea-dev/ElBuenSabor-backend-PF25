package com.buenSabor.BackEnd.dto.company.empresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponseDTO {

    private Long id;
    private String nombre;
    private String razonSocial;
    private String cuil;
    private boolean existe;
}