package com.buenSabor.BackEnd.dto.websocket;

import com.buenSabor.BackEnd.enums.TypeState;
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
    private TypeState nuevoEstadoId;
    private String tiempoEstimado; 
}
