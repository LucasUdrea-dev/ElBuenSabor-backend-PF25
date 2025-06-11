/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.promocionArticulo;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloDTO;
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
@AllArgsConstructor
@NoArgsConstructor
public class PromocionArticuloDTO {
     
    private Long id;
    private ArticuloDTO articulo;

    // La cantidad de ese artículo para esta promoción específica
    private int cantidad;
}

