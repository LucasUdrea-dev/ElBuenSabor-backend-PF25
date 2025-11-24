/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.articulo;

import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaDTO;
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
public class ArticuloUpdateDTO {
    
    protected String nombre;
   
    protected String descripcion;
    
    protected Double precio;
   
    protected Boolean existe;
   
    protected Boolean esParaElaborar;
    
    protected String imagenArticulo;

    protected UnidadMedidaDTO unidadMedida;

    
}
