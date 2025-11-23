/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.company.sucursal;

import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionCadenaSimpleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author oscarloha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalCadenaSimpleDTO {
    
    private Long id;
    private String nombre;
    private String horaApertura;
    private String horaCierre;
    private Boolean existe;
    private DireccionCadenaSimpleDTO direccion;
}
