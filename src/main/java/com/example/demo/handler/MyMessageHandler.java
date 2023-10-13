package com.example.demo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.StringTokenizer;

@Component
public class MyMessageHandler extends TextWebSocketHandler {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println(message);
        System.out.println(payload);
        //проверка аутентифицирован ли пользователь и аутентификация по маршруту topic.
    }

}