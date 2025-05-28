/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.manufacturado;

import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleInsumoCreateDTO;
import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloCreateDTO;
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
public class ArticuloManufacturadoCreateDTO extends ArticuloCreateDTO {

    private String tiempoEstimado;
    private String preparacion;
    
    private List<ArticuloManufacturadoDetalleInsumoCreateDTO>detalles;
}
