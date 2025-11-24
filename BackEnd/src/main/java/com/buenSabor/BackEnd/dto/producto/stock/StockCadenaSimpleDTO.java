/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.stock;

import com.buenSabor.BackEnd.dto.company.sucursal.SucursalCadenaSimpleDTO;
import com.buenSabor.BackEnd.dto.producto.insumo.InsumoResponseDTO;
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
public class StockCadenaSimpleDTO {
   
    private Long id;
   
    private Integer minStock;
    
    private Integer cantidad;

    private InsumoResponseDTO articuloInsumo;

    private SucursalCadenaSimpleDTO sucursal;
    
}
