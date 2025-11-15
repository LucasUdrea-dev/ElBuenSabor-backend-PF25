
package com.buenSabor.BackEnd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

   
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        
       //Destinos para el cliente (Suscripciones)
        registry.enableSimpleBroker("/topic", "/queue");

       //Destinos para el servidor (Envío de mensajes)
        registry.setApplicationDestinationPrefixes("/app");
    }

  
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint para conectarse: //localhost:8080/ws
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
