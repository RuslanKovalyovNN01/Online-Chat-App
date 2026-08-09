package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyStompSessionHandler extends  StompSessionHandlerAdapter {

    private String username;
    private MessageListener messageListener;

    public MyStompSessionHandler(MessageListener messageListener, String username) {
        this.messageListener = messageListener;
        this.username = username;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders){
        //System.out.println("Client Connected");
        //System.out.println("Before subscribing");

        try {
            session.subscribe("/topic/messages", new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return Message.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    try {
                        if (payload instanceof Message) {

                            Message message = (Message) payload;
                            messageListener.onMessageReceived(message);
                            System.out.println("Received message: " + message.getUser() + ": " + message.getMessage());
                        } else {
                            System.out.println("Received unexpected payload type: " + payload.getClass());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("After subscribing");
        System.out.println("Client Subscribe to /topic/messages");
        //session.send("/app/connect", username);
        //session.send("/app/request-users", "");
        session.subscribe("/topic/users", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return new ArrayList<String>().getClass();
            }

            @Override
            public void handleFrame(StompHeaders headers, @Nullable Object payload) {
                try {
                    if(payload instanceof ArrayList){
                        ArrayList<String> activeUsers = (ArrayList<String>) payload;
                        messageListener.onActiveUserUpdated(activeUsers);
                        System.out.println("Received active users " + activeUsers);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Subscribed to /topic/users");
        session.send("/app/connect", username);
        session.send("/app/request-users", "");
    }
    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        exception.printStackTrace();
    }
}
