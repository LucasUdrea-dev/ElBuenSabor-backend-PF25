/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.stock;

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
public class StockResponseDTO {
    private Long id;
    private Integer cantidad;
    private Integer minStock;
    
    
    private String sucursalNombre; 
    private Long sucursalId;
    
    
    private String articuloInsumoDenominacion;
    private Long articuloInsumoId;
}