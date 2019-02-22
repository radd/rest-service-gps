package io.github.radd.mgrgpsserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic/"); // topic is for subscriber - publisher
        config.setApplicationDestinationPrefixes("/ws"); // for send messages e.g. wsClient.send('/ws/'@MessageMapping',...
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect-ws").setAllowedOrigins("*"); // for connect to ws server e.g. Stomp.overWS('ws://localhost:8080/connect-ws');
    }

}