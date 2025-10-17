package com.buenSabor.BackEnd.dto.venta.envio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoEnvioDTO {
    private Long id;

    private String tipoDelivery;
}
