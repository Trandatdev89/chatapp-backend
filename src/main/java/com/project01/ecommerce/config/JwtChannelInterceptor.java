package com.project01.ecommerce.config;

import com.project01.ecommerce.entity.UserEntity;
import com.project01.ecommerce.model.request.TokenRequest;
import com.project01.ecommerce.services.AuthServices;
import com.project01.ecommerce.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final AuthServices authServices;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() != null && accessor.getCommand().equals(StompCommand.CONNECT)) {
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token == null || !authServices.introspecToken(TokenRequest.builder().token(token).build()).isValid()) {
                throw new IllegalArgumentException("Invalid or missing JWT token");
            }
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            accessor.setUser(() -> userId); // Gán userId làm Principal
        }

        return message;
    }
}
