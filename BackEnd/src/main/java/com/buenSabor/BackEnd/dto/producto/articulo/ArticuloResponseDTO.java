/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.articulo;

import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaSimplDTO;
import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaResponseDTO;
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
public class ArticuloResponseDTO {
    
    protected Long id;  
    protected String nombre;  
    protected String descripcion;  
    protected Double precio;  
    protected Boolean existe;  
    protected Boolean esParaElaborar;  
    protected String imagenArticulo;  
      
   
    protected SubcategoriaSimplDTO subcategoria;  
    protected UnidadMedidaResponseDTO unidadMedida;  
}
