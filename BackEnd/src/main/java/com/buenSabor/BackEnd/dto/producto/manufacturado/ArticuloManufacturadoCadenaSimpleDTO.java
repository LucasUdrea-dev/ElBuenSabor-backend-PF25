/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.manufacturado;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.producto.manufacturadodetalle.ArticuloManufacturadoDetalleCadenaSimpleDTO;
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
public class ArticuloManufacturadoCadenaSimpleDTO extends ArticuloCadenaSimpleDTO{
    

    private String tiempoEstimado;
    
    private String preparacion;
    
    private SucursalCadenaSimpleDTO sucursal;
    
    private List<ArticuloManufacturadoDetalleCadenaSimpleDTO> insumos;
    
}
