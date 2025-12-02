package com.buenSabor.BackEnd.services;

import com.buenSabor.BackEnd.dto.venta.pedido.PedidoConDireccionDTO;
import com.buenSabor.BackEnd.enums.TypeState;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    // ----------------------------------------------------------------------
    // 1. MENSAJES PARA EL CLIENTE FINAL
    // ----------------------------------------------------------------------
    private static final Map<TypeState, String> MESSAGES_FOR_CLIENT = Map.of(
            TypeState.STANDBY, "Tu pedido ha sido recibido y espera confirmación.",
            TypeState.PREPARING, "¡Manos a la obra! La cocina está preparando tu pedido.",
            TypeState.INCOMING, "¡Tu pedido está en camino!",
            TypeState.DELIVERED, "Pedido entregado. ¡Esperamos que lo disfrutes!",
            TypeState.CANCELLED, "Tu pedido ha sido cancelado.",
            TypeState.REJECTED, "Tu pedido ha sido rechazado.");

    // ----------------------------------------------------------------------
    // 2. REGLAS DE NEGOCIO PARA DASHBOARDS
    // ----------------------------------------------------------------------

    // CAJERO: Ve pedidos nuevos para confirmar, o cancelados/rechazados para
    // gestionar devoluciones
    private static final List<TypeState> STATES_FOR_CAJERO = List.of(
            TypeState.INCOMING,
            TypeState.STANDBY,
            TypeState.READY,
            TypeState.REJECTED,
            TypeState.CANCELLED,
            TypeState.DELIVERED);

    // COCINERO: Ve pedidos para aceptar (STANDBY) y los que están en curso
    // (PREPARING)
    private static final List<TypeState> STATES_FOR_COCINA = List.of(
            TypeState.STANDBY,
            TypeState.PREPARING);

    // DELIVERY: Ve cuando sale el pedido (INCOMING) o cuando se entrega (DELIVERED)
    private static final List<TypeState> STATES_FOR_DELIVERY = List.of(
            TypeState.DELIVERING);

    // ----------------------------------------------------------------------
    // LÓGICA DE ENVÍO
    // ----------------------------------------------------------------------

 
    public void notifyStatusChange(Long orderId, TypeState state, PedidoConDireccionDTO orderDto) {

        String clientMessage = MESSAGES_FOR_CLIENT.getOrDefault(state, "Estado actualizado");

        Map<String, Object> response = Map.of(
                "message", clientMessage,
                "pedido", orderDto);

        //  cliente
        if (orderDto.getUsuario() != null && orderDto.getUsuario().getId() != null) {
        String userChannel = "/topic/usuarios/" + orderDto.getUsuario().getId();
        messagingTemplate.convertAndSend(userChannel, response);
    }

        //roles correspondientes según el estado
        if (STATES_FOR_CAJERO.contains(state)) {
            messagingTemplate.convertAndSend("/topic/dashboard/cajero", orderDto);
        }
        if (STATES_FOR_COCINA.contains(state)) {
            messagingTemplate.convertAndSend("/topic/dashboard/cocina", orderDto);
        }
        if (STATES_FOR_DELIVERY.contains(state)) {
            messagingTemplate.convertAndSend("/topic/dashboard/delivery", orderDto);
        }
        messagingTemplate.convertAndSend("/topic/dashboard/admin", orderDto);
    }
}