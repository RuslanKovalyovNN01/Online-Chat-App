package com.example.websocket_demo.client;

import com.example.websocket_demo.Message;
import jdk.jshell.execution.Util;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//public class ClientGUI {
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        MyStompClient myStompClient = new MyStompClient("Ruslan");
//        myStompClient.sendMessage(new Message("Ruslan", "Hello, Ruslan"));
//        myStompClient.disconnectUser("Ruslan");
//        new java.util.concurrent.CountDownLatch(1).await();
//    }
//}

public class ClientGUI extends JFrame implements MessageListener{
    private JPanel connectedUsersPanel, messagePanel;
    private MyStompClient myStompClient;
    private String username;
    private JScrollPane  messagePanelScrollPane;
    public ClientGUI(String username) throws ExecutionException, InterruptedException {
        super("User: " + username);
        this.username = username;
        myStompClient = new MyStompClient(this,username);
        setSize(1218,685);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                    int option = JOptionPane.showConfirmDialog(ClientGUI.this, "Do you want to leave?", "Exit", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        myStompClient.disconnectUser(username);
                        ClientGUI.this.dispose();
                    }
                }

        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateMessageSize();
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

        messagePanelScrollPane = new JScrollPane(messagePanel);
        messagePanelScrollPane.setBackground(Utilities.TRANSPARENT_COLOR);
        messagePanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messagePanelScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        messagePanelScrollPane.getViewport().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                revalidate();
                repaint();
            }
        });

        chatPanel.add(messagePanelScrollPane, BorderLayout.CENTER);

        //messagePanel.add(createChatMessageComponent(new Message("Ruslan", "Hello!")));


        //JLabel message = new JLabel("Hi everyone");
        //message.setFont(new Font("Inter", Font.BOLD, 18));
        //message.setForeground(Utilities.TEXT_COLOR);
        //messagePanel.add(message);

        JPanel inputPanel= new JPanel();
        inputPanel.setBorder(Utilities.addPadding(10,10,10,10));
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Utilities.TRANSPARENT_COLOR);

        JTextField inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    String input = inputField.getText();
                    if(input.isEmpty()) return;
                    inputField.setText("");
//                  //messagePanel.add(createChatMessageComponent(new Message("Ruslan",input)));
//                  //repaint();
//                   //revalidate();
                    myStompClient.sendMessage(new Message(username, input));
                }
            }
        });
        inputField.setBackground(Utilities.SECONDARY_COLOR);
        inputField.setForeground(Utilities.TEXT_COLOR);
        inputField.setBorder(Utilities.addPadding(0,10,0,10));
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

        int width = (int) (getWidth() * 0.6);
        JLabel messageLabel = new JLabel();
        messageLabel.setText(
                "<html><body style='width:" + width + "px'>" +
                        message.getMessage() +
                        "</body></html>"
        );
        messageLabel.setFont(new Font("Inter", Font.PLAIN, 18));
        messageLabel.setForeground(Utilities.TEXT_COLOR);
        chatMessage.add(messageLabel);

        return chatMessage;
    }

    @Override
    public void onMessageReceived(Message message) {
        //System.out.println("onMessageRecieve");
        messagePanel.add(createChatMessageComponent(message));
        revalidate();
        repaint();
        messagePanelScrollPane.getVerticalScrollBar().setValue(Integer.MAX_VALUE);
        //revalidate();
    }

    @Override
    public void onActiveUserUpdated(ArrayList<String> users) {
        if(connectedUsersPanel.getComponents().length >= 2){
            connectedUsersPanel.remove(1);
        }
        JPanel usersListPanel = new JPanel();
        usersListPanel.setBackground(Utilities.TRANSPARENT_COLOR);
        usersListPanel.setLayout(new BoxLayout(usersListPanel, BoxLayout.Y_AXIS));
        for(String user: users){
            JLabel username = new JLabel();
            username.setText(user);
            username.setForeground(Utilities.TEXT_COLOR);
            username.setFont(new Font("Inter", Font.BOLD, 16));
            usersListPanel.add(username);
        }
        connectedUsersPanel.add(usersListPanel);
        revalidate();
        repaint();
    }
    private void updateMessageSize() {
        for(int i = 0; i < messagePanel.getComponents().length; i++){
            Component component = messagePanel.getComponent(i);
            if(component instanceof JPanel){
                JPanel chatMessage = (JPanel) component;
                if(chatMessage.getComponent(1) instanceof JLabel){
                    JLabel messageLabel = (JLabel) chatMessage.getComponent(1);
                    messageLabel.setText(
                            "<html><body style='width:" + (0.60 * getWidth()) + "px'>" +
                                    messageLabel.getText() +
                                    "</body></html>"
                    );
                }
            }
        }
    }
}
