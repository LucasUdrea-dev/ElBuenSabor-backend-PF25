/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.categoria;

import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaCadenaSimpleDTO;
import java.util.List;
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
public class CategoriaCadenaSimpleDTO {
    
    private Long id;
    
    private String denominacion;
    
    private String imagen;
    
    private List<SubcategoriaCadenaSimpleDTO> subcategorias;

    private boolean esParaElaborar;
    
}
