/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.manufacturadodetalle;

import com.buenSabor.BackEnd.dto.producto.insumo.InsumoCadenaSimpleDTO;
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
public class ArticuloManufacturadoDetalleCadenaSimpleDTO {
    
    private Long id;
    
    private InsumoCadenaSimpleDTO articuloInsumo;
    
    private int cantidad;
}
