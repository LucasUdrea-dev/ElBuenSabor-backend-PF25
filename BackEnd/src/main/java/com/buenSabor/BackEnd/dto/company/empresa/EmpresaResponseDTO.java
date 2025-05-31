/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.company.empresa;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalResponseDTO;
import java.util.List;
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
public class EmpresaResponseDTO {
    
    private Long id;  
    private String nombre;  
    private String razonSocial;  
    private String cuil;  
      
    private List<SucursalResponseDTO>sucursales;
    
}
