package com.threektechone.resorthub.config.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.threektechone.resorthub.config.security.UserDetails.UserDetailsServiceImpl;
import com.threektechone.resorthub.service.auth.JwtService;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtStompChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) {
            return message;
        }

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (!StringUtils.hasText(authHeader)) {
                throw new MessagingException("Missing Authorization header for STOMP CONNECT");
            }

            String token = stripBearer(authHeader);
            if (!StringUtils.hasText(token)) {
                throw new MessagingException("Invalid Authorization header for STOMP CONNECT");
            }

            try {
                String email = jwtService.extractEmail(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (!jwtService.isTokenValid(token, userDetails)) {
                    throw new MessagingException("Invalid JWT for STOMP CONNECT");
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                accessor.setUser(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | IllegalArgumentException ex) {
                throw new MessagingException("Invalid JWT for STOMP CONNECT", ex);
            } finally {
                SecurityContextHolder.clearContext();
            }
        }

        return message;
    }

    private static String stripBearer(String authHeader) {
        String trimmed = authHeader.trim();
        if (trimmed.regionMatches(true, 0, "Bearer", 0, "Bearer".length())) {
            return trimmed.substring("Bearer".length()).trim();
        }
        return trimmed;
    }
}

