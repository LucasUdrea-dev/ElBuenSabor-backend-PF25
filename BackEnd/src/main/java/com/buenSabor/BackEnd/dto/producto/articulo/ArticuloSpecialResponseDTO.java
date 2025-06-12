/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.articulo;

import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleInsumoDTO;
import com.buenSabor.BackEnd.dto.producto.medida.UnidadMedidaDTO;
import com.buenSabor.BackEnd.dto.producto.subcategoria.SubcategoriaDTO;
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
      
   
    private SubcategoriaDTO subcategoria;  
    private UnidadMedidaDTO unidadMedida;  
    
    //MANUFACTURADO
    private String tiempoEstimado;
    private  List<ArticuloManufacturadoDetalleInsumoDTO>detalleInsumos;
    
    
}
