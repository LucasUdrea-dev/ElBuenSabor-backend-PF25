/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.company.empresa;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalDTO;
import java.util.List;
import lombok.Data;

/**
 *
 * @author oscarloha
 */
@Data
public class EmpresaDTO {

    private Long id;
    private String nombre;
    private String razonSocial;
    private String cuil;
    private boolean existe;

}
