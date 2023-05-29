/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.core;

/**
 *
 * @author datcy
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatRoomFrm extends JFrame {
    private JTextField roomNameField;
    private JButton createButton;

    public ChatRoomFrm() {
        setTitle("Create Chatroom");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 150));

        // Create components
        JLabel nameLabel = new JLabel("Chatroom Name:");
        roomNameField = new JTextField(20);
        createButton = new JButton("Create");

        // Create layout
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 1, 5, 5));
        formPanel.add(nameLabel);
        formPanel.add(roomNameField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createButton);

        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        pack();

        // Add action listener to the create button
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomName = roomNameField.getText();
                createChatroom(roomName);
            }
        });
    }

    private void createChatroom(String roomName) {
        // TODO: Implement the logic to create the chatroom
        // You can use the entered room name to create a new Chatroom object or perform any other actions.
        System.out.println("Creating chatroom with name: " + roomName);
    }
}

