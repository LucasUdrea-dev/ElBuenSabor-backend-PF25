/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.pago;

import com.buenSabor.BackEnd.dto.venta.mercadopago.MercadoPagoCreateDTO;
import java.util.List;
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
public class TipoPagoCreateDTO {
    private Long id;
    private String denominacion; 
    private List<MercadoPagoCreateDTO>mercadoPago;
}
