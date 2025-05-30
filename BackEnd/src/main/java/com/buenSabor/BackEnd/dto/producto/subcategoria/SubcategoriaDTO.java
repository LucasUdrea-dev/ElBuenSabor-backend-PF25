/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.subcategoria;

import com.buenSabor.BackEnd.dto.producto.categoria.CategoriaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class SubcategoriaDTO {


    private String denominacion;
    private CategoriaDTO categoria;

    // Relaciones omitidas: articuloList
}
