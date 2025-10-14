package com.buenSabor.BackEnd.dto.venta.mercadopago;

import com.buenSabor.BackEnd.dto.venta.pago.TipoPagoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
