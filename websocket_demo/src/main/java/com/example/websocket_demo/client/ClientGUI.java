package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;
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
    }
    private void addGUIComponents() {
        addConnectedUsersComponents();
        addChatComponents();
    }
    private void addConnectedUsersComponents() {
        connectedUsersPanel = new JPanel();
        connectedUsersPanel.setBorder(Utilities.addPadding(10,10,10,10));
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

        JPanel inputPanel= new JPanel();
        inputPanel.setBorder(Utilities.addPadding(10,10,10,10));
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Utilities.TRANSPARENT_COLOR);

        JTextField inputField = new JTextField();
        inputField.setBackground(Utilities.SECONDARY_COLOR);
        inputField.setForeground(Utilities.TEXT_COLOR);
        inputField.setFont(new Font("Inter", Font.PLAIN, 16));
        inputField.setPreferredSize(new Dimension(inputPanel.getWidth(),50));
        inputPanel.add(inputField, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        add(chatPanel, BorderLayout.CENTER);
    }
    private JPanel createChatMessageComponent(Message message){
        JPanel chatMessage = new JPanel();
        chatMessage.setBackground(Utilities.TRANSPARENT_COLOR);
        chatMessage.setLayout(new BoxLayout(chatMessage, BoxLayout.Y_AXIS));
        chatMessage.setBorder(Utilities.addPadding(20,20,10,20));

        JLabel usernameLabel = new JLabel(message.getUser());
        usernameLabel.setFont(new Font("Inter", Font.BOLD, 18));
        usernameLabel.setForeground(Utilities.TEXT_COLOR);
        chatMessage.add(usernameLabel);
        
        return chatMessage;
    }
}
