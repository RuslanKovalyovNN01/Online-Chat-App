package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;
import java.util.ArrayList;

public interface MessageListener {
    void onMessageReceived(Message message);
    void onActiveUserUpdated(ArrayList<String> users);
}
