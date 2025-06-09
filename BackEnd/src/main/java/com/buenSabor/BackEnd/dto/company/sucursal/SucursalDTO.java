/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.company.sucursal;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author oscarloha
 */
@Getter  
@Setter  
@NoArgsConstructor  
@AllArgsConstructor 
@JsonIgnoreProperties(ignoreUnknown = true)
public class SucursalDTO {

    private String nombre;
    private String horaApertura;
    private String horaCierre;
    private Boolean existe;

    private DireccionDTO direccion;
    private Long empresaId;

    // Relaciones omitidas:
    // - empleadoList
    // - stockArticuloInsumoList
    // - pedidoList
    // - promocionList
}