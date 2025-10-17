package com.buenSabor.BackEnd.dto.ubicacion.direccion;

import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadDTO;
import lombok.Data;

@Data
public class DireccionDTO {
    private Long id;
    private String nombreCalle;
    private String numeracion;
    private Double latitud;
    private Double longitud;
    private String alias;
    private String descripcionEntrega;
    private CiudadDTO ciudad;
}