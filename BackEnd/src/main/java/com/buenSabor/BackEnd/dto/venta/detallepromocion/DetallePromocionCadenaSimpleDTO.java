/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.detallepromocion;

import com.buenSabor.BackEnd.dto.venta.promocion.PromocionCadenaSimpleDTO;
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
public class DetallePromocionCadenaSimpleDTO {
    
    private Long id;
    
    private PromocionCadenaSimpleDTO promocion; 
    
    private int cantidad;
    
}
