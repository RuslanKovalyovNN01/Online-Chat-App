package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutionException;

//public class ClientGUI {
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        MyStompClient myStompClient = new MyStompClient("Ruslan");
//        myStompClient.sendMessage(new Message("Ruslan", "Hello, Ruslan"));
//        myStompClient.disconnectUser("Ruslan");
//        new java.util.concurrent.CountDownLatch(1).await();
//    }
//}

public class ClientGUI extends JFrame {
    public ClientGUI(String username) {
        super("User: " + username);
        setSize(1218,685);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ClientGUI.this, "Do you want to leave?", "Exit", JOptionPane.YES_NO_OPTION);

                if(option == JOptionPane.YES_OPTION) {
                    ClientGUI.this.dispose();
                }
            }
        });
    }
}
