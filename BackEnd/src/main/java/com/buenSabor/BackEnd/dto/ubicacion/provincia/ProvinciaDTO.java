package com.buenSabor.BackEnd.dto.ubicacion.provincia;

import com.buenSabor.BackEnd.dto.ubicacion.pais.PaisDTO;
import lombok.Data;

@Data
public class ProvinciaDTO {
    private Long id;
    private String nombre;
    private PaisDTO pais;
}