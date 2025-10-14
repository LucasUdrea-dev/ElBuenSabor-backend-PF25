package com.buenSabor.BackEnd.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request para cambiar el estado de un pedido desde el frontend
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CambioEstadoPedidoRequest {
    private Long pedidoId;
    private Long nuevoEstadoId;
    private String tiempoEstimado; // Opcional, puede actualizarse junto con el estado
}
