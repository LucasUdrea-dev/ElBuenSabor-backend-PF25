/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.ubicacion.direccion;

import com.buenSabor.BackEnd.dto.ubicacion.ciudad.CiudadCreateDTO;
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
public class DireccionCreateDTO {
    
    
    protected Boolean existe;
    protected String nombreCalle;
    protected String numeracion;
    protected Double latitud;
    protected Double longitud;
    
    protected CiudadCreateDTO ciudad;
    
}
