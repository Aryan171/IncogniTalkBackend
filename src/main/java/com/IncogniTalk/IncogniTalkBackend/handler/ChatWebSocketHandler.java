package com.IncogniTalk.IncogniTalkBackend.handler;

import com.IncogniTalk.IncogniTalkBackend.dto.WebSocketMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserId(session);
        if (userId != null) {
            sessions.put(userId, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = getUserId(session);
        if (userId != null) {
            sessions.remove(userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        WebSocketMessage webSocketMessage = objectMapper.readValue(message.getPayload(), WebSocketMessage.class);
        WebSocketSession receiverSession = sessions.get(webSocketMessage.getReceiverId());

        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(webSocketMessage)));
        } else {
            // TODO: Trigger FCM push notification
        }
    }

    public void sendControlMessage(String userId, String controlMessage) throws IOException {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(controlMessage));
        }
    }

    private String getUserId(WebSocketSession session) {
        if (session.getUri() != null) {
            String path = session.getUri().getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        }
        return null;
    }
}
