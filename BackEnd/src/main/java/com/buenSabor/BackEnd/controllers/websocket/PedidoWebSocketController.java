package com.buenSabor.BackEnd.controllers.websocket;

import com.buenSabor.BackEnd.dto.websocket.CambioEstadoPedidoRequest;
import com.buenSabor.BackEnd.dto.websocket.PedidoNotificacion;
import com.buenSabor.BackEnd.models.venta.EstadoPedido;
import com.buenSabor.BackEnd.models.venta.Pedido;
import com.buenSabor.BackEnd.repositories.venta.EstadoPedidoRepository;
import com.buenSabor.BackEnd.repositories.venta.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Controller
public class PedidoWebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoWebSocketController.class);

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/pedido.cambiarEstado")
    @Transactional
    public void cambiarEstadoPedido(CambioEstadoPedidoRequest request) {
        try {
            Pedido pedido = pedidoRepository.findById(request.getPedidoId())
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
            EstadoPedido nuevoEstado = estadoPedidoRepository.findById(request.getNuevoEstadoId())
                    .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

            pedido.setEstadoPedido(nuevoEstado);
            if (request.getTiempoEstimado() != null) {
                pedido.setTiempoEstimado(request.getTiempoEstimado());
            }
            pedidoRepository.save(pedido);

            // Construir notificación
            PedidoNotificacion notif = new PedidoNotificacion();
            notif.setPedidoId(pedido.getId());
            notif.setEstadoId(nuevoEstado.getId());
            notif.setEstadoNombre(nuevoEstado.getNombreEstado().name());
            notif.setTiempoEstimado(pedido.getTiempoEstimado());
            notif.setFecha(pedido.getFecha());
            notif.setTimestamp(new Date());
            
            // Información del usuario
            if (pedido.getUsuario() != null) {
                notif.setUsuarioId(pedido.getUsuario().getId());
                notif.setUsuarioNombre(pedido.getUsuario().getNombre());
            }
            
            // Información de la sucursal
            if (pedido.getSucursal() != null) {
                notif.setSucursalId(pedido.getSucursal().getId());
            }
            
            // Enviar notificaciones a diferentes topics según el contexto
            
            // 1. Notificación al usuario específico (cliente que hizo el pedido)
            if (pedido.getUsuario() != null) {
                messagingTemplate.convertAndSend(
                    "/topic/pedidos/usuario/" + pedido.getUsuario().getId(), 
                    notif
                );
                logger.info("Notificación enviada al usuario: {}", pedido.getUsuario().getId());
            }
            
            // 2. Notificación a la sucursal específica (cocineros, delivery de esa sucursal)
            if (pedido.getSucursal() != null) {
                messagingTemplate.convertAndSend(
                    "/topic/pedidos/sucursal/" + pedido.getSucursal().getId(), 
                    notif
                );
                logger.info("Notificación enviada a sucursal: {}", pedido.getSucursal().getId());
            }
            
            // 3. Notificación general para administradores (ven todos los pedidos)
            messagingTemplate.convertAndSend("/topic/pedidos/admin", notif);
            
            // 4. Notificación general (backward compatibility)
            messagingTemplate.convertAndSend("/topic/pedidos", notif);
            
            logger.info("Estado actualizado - Pedido: {}, Estado: {}", pedido.getId(), nuevoEstado.getNombreEstado());
        } catch (Exception e) {
            logger.error("Error al cambiar estado: {}", e.getMessage());
        }
    }
}
