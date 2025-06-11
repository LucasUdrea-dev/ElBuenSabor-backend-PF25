/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.company.sucursal;

import com.buenSabor.BackEnd.dto.company.empresa.EmpresaDTO;
import com.buenSabor.BackEnd.dto.ubicacion.direccion.DireccionDTO;
import lombok.Data;

/**
 *
 * @author oscarloha
 */
@Data
public class SucursalDTO {
    private Long id;
    private String nombre;
    private String horaApertura;
    private String horaCierre;
    private Boolean existe;
    private DireccionDTO direccion;
    private EmpresaDTO empresa;
}