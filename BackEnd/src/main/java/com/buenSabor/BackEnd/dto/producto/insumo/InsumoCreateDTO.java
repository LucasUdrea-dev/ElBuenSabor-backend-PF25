/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.insumo;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloCreateDTO;
import com.buenSabor.BackEnd.dto.producto.stock.StockCreateDTO;
import jakarta.validation.constraints.NotNull;
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
public class InsumoCreateDTO extends ArticuloCreateDTO{
    
    @NotNull(message = "El precio del articulo es obligatorio")
    private Double precioCompra;
    
//    @NotNull(message = "El stock del articulo es obligatorio")
    private StockCreateDTO stockArticuloInsumo;
    
}
