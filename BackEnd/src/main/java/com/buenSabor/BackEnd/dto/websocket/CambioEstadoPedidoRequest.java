package com.buenSabor.BackEnd.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CambioEstadoPedidoRequest {
    private Long pedidoId;
    private Long nuevoEstadoId;
    private String tiempoEstimado;
}
