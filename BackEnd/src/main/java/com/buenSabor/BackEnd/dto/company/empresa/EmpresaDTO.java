package com.buenSabor.BackEnd.dto.company.empresa;

import lombok.Data;

@Data
public class EmpresaDTO {

    private Long id;
    private String nombre;
    private String razonSocial;
    private String cuil;
    private boolean existe;

}
