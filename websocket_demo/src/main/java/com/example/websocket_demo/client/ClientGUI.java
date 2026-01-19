package com.example.websocket_demo.client;

import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//public class ClientGUI {
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        MyStompClient myStompClient = new MyStompClient("Ruslan");
//        myStompClient.sendMessage(new Message("Ruslan", "Hello, Ruslan"));
//        myStompClient.disconnectUser("Ruslan");
//        new java.util.concurrent.CountDownLatch(1).await();
//    }
//}

public class ClientGUI extends JFrame {
    private JPanel connectedUsersPanel, messagePanel;
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
        getContentPane().setBackground(Utilities.PRIMARY_COLOR);
        addGUIComponents();
        addChatComponents();
    }
    private void addGUIComponents() {
        addConnectedUsersComponents();
    }
    private void addConnectedUsersComponents() {
        connectedUsersPanel = new JPanel();
        connectedUsersPanel.setLayout(new BoxLayout(connectedUsersPanel, BoxLayout.Y_AXIS));
        connectedUsersPanel.setBackground(Utilities.SECONDARY_COLOR);
        connectedUsersPanel.setPreferredSize(new Dimension(200, getHeight()));
        JLabel connectedUsersLabel = new JLabel("Connected Users");
        connectedUsersLabel.setFont(new Font("Inter", Font.BOLD, 18));
        connectedUsersLabel.setForeground(Utilities.TEXT_COLOR);
        connectedUsersPanel.add(connectedUsersLabel);
        add(connectedUsersPanel, BorderLayout.WEST);
    }
    private void addChatComponents() {
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.setBackground(Utilities.TRANSPARENT_COLOR);
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Utilities.TRANSPARENT_COLOR);
        chatPanel.add(messagePanel, BorderLayout.CENTER);
        JLabel message = new JLabel("Hi everyone");
        message.setFont(new Font("Inter", Font.BOLD, 18));
        message.setForeground(Utilities.TEXT_COLOR);
        messagePanel.add(message);
        add(chatPanel, BorderLayout.CENTER);
    }
}
