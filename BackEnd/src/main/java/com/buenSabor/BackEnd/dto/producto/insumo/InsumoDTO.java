/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.insumo;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
import com.buenSabor.BackEnd.dto.producto.stock.StockDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class InsumoDTO extends ArticuloDTO{
    
    private Long id;
    private Double precioCompra;
    private StockDTO stockArticuloInsumo;
    
}
