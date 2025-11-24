/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.stock;

import jakarta.validation.constraints.NotNull;
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
public class StockCreateDTO {
    
    @NotNull(message = "El min stock es obligatorio")
    private Integer minStock;
    
    @NotNull(message = "La cantidad Stock es obligatorio")
    private Integer cantidad;

    @NotNull(message = "El id de articulo Insumo es obligatorio")
    private Long articuloInsumoId;

    @NotNull(message = "El id sucursa les obligatorio es obligatorio")
    private Long sucursalId;

    
}
