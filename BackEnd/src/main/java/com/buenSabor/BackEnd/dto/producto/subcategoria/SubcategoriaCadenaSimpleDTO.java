/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.subcategoria;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloCadenaSimpleDTO;
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
public class SubcategoriaCadenaSimpleDTO {
    
    private Long id; 
    
    private String denominacion;
    
    private ArticuloCadenaSimpleDTO categoriaId;
    
}
