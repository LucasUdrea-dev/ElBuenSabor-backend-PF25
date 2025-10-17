package com.buenSabor.BackEnd.dto.venta.pago;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoPagoDTO {
    private Long id;
    private String tipoPago;
}
