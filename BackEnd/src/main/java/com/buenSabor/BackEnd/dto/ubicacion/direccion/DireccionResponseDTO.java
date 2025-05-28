/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.ubicacion.direccion;

import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadResponseDTO;
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
public class DireccionResponseDTO {
    
    private Long id; 
    private Boolean existe;
    private String nombreCalle;
    private String numeracion;
    private Double latitud;
    private Double longitud;
    private String alias;
    private String descripcionEntrega;       
    
    
    private CiudadResponseDTO ciudad;
    
}
