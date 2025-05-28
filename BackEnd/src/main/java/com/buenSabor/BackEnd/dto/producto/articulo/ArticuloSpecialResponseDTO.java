/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.articulo;

import com.buenSabor.BackEnd.dto.producto.manufacturado.ArticuloManufacturadoDetalleInsumoResponseDTO;
import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaResponseDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaSimplDTO;
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
public class ArticuloSpecialResponseDTO {
    
    private Long id;  
    private String nombre;  
    private String descripcion;  
    private Double precio;  
    private Boolean existe;  
    private Boolean esParaElaborar;  
    private String imagenArticulo;  
      
   
    private SubcategoriaSimplDTO subcategoria;  
    private UnidadMedidaResponseDTO unidadMedida;  
    
    private String tiempoEstimado;
    private  List<ArticuloManufacturadoDetalleInsumoResponseDTO>detalleInsumos;
    
    
}
