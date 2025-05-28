/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.categoria;

import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaResponseDTO;
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
public class CategoriaResponseDTO {
    
    private Long id;
    private String denominacion;
    private String imagen;
    private List<SubcategoriaResponseDTO>subcategorias;
    
}
