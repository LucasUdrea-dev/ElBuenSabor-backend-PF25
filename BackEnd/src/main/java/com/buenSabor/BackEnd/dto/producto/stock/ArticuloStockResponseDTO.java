/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.stock;

import com.buenSabor.BackEnd.dto.producto.insumo.ArticuloInsumoResponseDTO;
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
public class ArticuloStockResponseDTO {
    
    private Long id;
    private Integer maxStock;
    private Integer cantidad;
    private ArticuloInsumoResponseDTO articuloInsumo;
}
