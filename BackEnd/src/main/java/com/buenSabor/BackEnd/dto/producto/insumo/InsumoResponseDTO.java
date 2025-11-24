/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.insumo;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloResponseDTO;
import com.buenSabor.BackEnd.dto.producto.stock.StockResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class InsumoResponseDTO extends ArticuloResponseDTO{  
    
    private Double precioCompra;


    
}
