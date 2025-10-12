/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.manufacturado;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleInsumoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticuloManufacturadoDTO extends ArticuloDTO {
    
    private Long id;
    private String tiempoEstimado;
    private String preparacion;
    private Long sucursalId;
    private List<ArticuloManufacturadoDetalleInsumoDTO>insumos;
    // Relaciones omitidas: detalleInsumos
}
