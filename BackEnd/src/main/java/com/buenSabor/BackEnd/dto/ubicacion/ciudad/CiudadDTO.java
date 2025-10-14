package com.buenSabor.BackEnd.dto.ubicacion.ciudad;

import com.buenSabor.BackEnd.dto.ubicacion.provincia.ProvinciaDTO;
import lombok.Data;

@Data
public class CiudadDTO {
    private Long id;
    private String nombre;
    private ProvinciaDTO provincia;
}