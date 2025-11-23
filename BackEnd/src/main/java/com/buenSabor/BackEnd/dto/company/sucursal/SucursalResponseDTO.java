/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.company.sucursal;

import com.buenSabor.BackEnd.dto.company.empresa.EmpresaResponseDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionResponseDTO;
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
public class SucursalResponseDTO {

    private Long id;

    private String nombre;

    private String horaApertura;

    private String horaCierre;

    private Boolean existe;

    private EmpresaResponseDTO empresa;

}
