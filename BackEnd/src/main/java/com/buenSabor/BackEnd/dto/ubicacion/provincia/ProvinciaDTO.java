/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.ubicacion.provincia;

import com.buenSabor.BackEnd.dto.ubicacion.pais.PaisDTO;
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
public class ProvinciaDTO {


    private String nombre;
    private PaisDTO pais;

    // Relaciones omitidas: ciudadList
}