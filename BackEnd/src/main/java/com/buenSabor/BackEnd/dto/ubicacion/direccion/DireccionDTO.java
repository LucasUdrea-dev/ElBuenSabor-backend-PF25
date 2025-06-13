/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.ubicacion.direccion;

import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadDTO;
import lombok.Data;

/**
 *
 * @author oscarloha
 */
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