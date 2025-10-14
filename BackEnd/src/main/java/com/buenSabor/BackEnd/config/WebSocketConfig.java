
package com.buenSabor.BackEnd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configura el Message Broker (intermediario de mensajes)
     * 
     * 1. /app - Para mensajes que van al servidor (el cliente envía al servidor)
     * 2. /topic - Para mensajes broadcast (el servidor envía a todos los clientes
     * suscritos)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita un broker simple en memoria
        // Los clientes se suscriben a /topic/* para recibir mensajes
        //
        // Topics disponibles:
        // - /topic/pedidos → Todos los pedidos (backward compatibility)
        // - /topic/pedidos/admin → Solo para administradores (todos los pedidos)
        // - /topic/pedidos/sucursal/{id} → Pedidos de una sucursal específica
        // (cocineros, delivery)
        // - /topic/pedidos/usuario/{id} → Pedidos de un usuario específico (cliente)
        config.enableSimpleBroker("/topic");

        // Los mensajes con destino /app/* serán enrutados a @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registra los endpoints STOMP (Simple Text Oriented Messaging Protocol)
     * 
     * STOMP es un protocolo que funciona sobre WebSocket
     * Es más fácil de usar que WebSocket puro
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint para conectarse: ws://localhost:8080/ws
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
