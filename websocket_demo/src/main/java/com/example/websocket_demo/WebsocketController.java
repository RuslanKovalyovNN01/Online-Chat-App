package com.example.websocket_demo;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;

@Controller
public class WebsocketController {
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public WebsocketController(SimpMessagingTemplate messgingTemplate){
        this.messagingTemplate = messgingTemplate;
    }
    @MessageMapping("/message")
    public void handleMessage(Message message) {
        System.out.println("Received message from user: " + message.getUser() + ": " + message.getMessage());
        messagingTemplate.convertAndSend("/topic/messages", message);
        System.out.println("Sent message to /topic/messages: " + message.getUser() + ": " + message.getMessage());
    }
}
