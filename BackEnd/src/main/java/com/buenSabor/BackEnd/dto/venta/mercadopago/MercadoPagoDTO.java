/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buenSabor.BackEnd.dto.venta.mercadopago;

import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
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
public class MercadoPagoDTO {
    private Long id;
 
    private Integer mpMerchantOrderId;
  
    private String mpPreferenceId;
  
    private String mpPaymentType;

    private TipoPagoDTO tipoPago;
}
