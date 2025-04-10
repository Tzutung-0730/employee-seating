package com.example.employee_seating_backend.config;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 這裡可以處理接收到的 WebSocket 消息
        System.out.println("Received message: " + message.getPayload());

        // 回應給客戶端
        session.sendMessage(new TextMessage("Message received: " + message.getPayload()));
    }
}
