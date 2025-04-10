package com.example.employee_seating_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket  // 啟用 WebSocket 支援
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 註冊 WebSocket 處理器，將 "/ws" 路徑與處理器綁定
        registry.addHandler(new MyWebSocketHandler(), "/ws")
                .setAllowedOrigins("http://localhost:8081");  // 允許來自前端的請求
    }
}
