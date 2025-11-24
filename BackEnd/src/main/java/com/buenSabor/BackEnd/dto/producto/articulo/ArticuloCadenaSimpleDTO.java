/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.articulo;

import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaResponseDTO;
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
public class ArticuloCadenaSimpleDTO {
    
    protected Long id;
    
    protected String nombre;
   
    protected String descripcion;
    
    protected Double precio;
   
    protected Boolean existe;
   
    protected Boolean esParaElaborar;
    
    protected String imagenArticulo;

    protected SubcategoriaResponseDTO subcategoria;

    protected UnidadMedidaDTO unidadMedida;

    
}
