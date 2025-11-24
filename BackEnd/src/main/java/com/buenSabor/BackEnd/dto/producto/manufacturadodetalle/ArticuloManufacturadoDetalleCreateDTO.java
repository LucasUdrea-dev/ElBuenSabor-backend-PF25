/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.producto.manufacturadodetalle;

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
public class ArticuloManufacturadoDetalleCreateDTO {

    @NotNull(message = "El detalle articulo del articulo es obligatorio")
    private Long articuloInsumoId;
    
    @NotNull(message = "El detalle cantidad del articulo es obligatorio")
    private int cantidad;
}
