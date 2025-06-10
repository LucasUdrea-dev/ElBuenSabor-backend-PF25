/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.promodetalle;

import com.buenSabor.BackEnd.dto.producto.articulo.ArticuloSpecialResponseDTO;
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
public class PromoArticuloResponseDTO {
    
        private Long id;
        private int cantidad; 
        private ArticuloSpecialResponseDTO articulo;
    
}
