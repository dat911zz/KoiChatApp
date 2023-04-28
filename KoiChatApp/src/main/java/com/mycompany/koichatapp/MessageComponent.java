/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author datcy
 */
public class MessageComponent extends JPanel {
    private JLabel messageLabel;
    
    public MessageComponent(String text, boolean isSentByUser) {
        setLayout(new BorderLayout());
        messageLabel = new JLabel(text);
        messageLabel.setOpaque(true);
        messageLabel.setMinimumSize(new Dimension(255, 20));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        if (isSentByUser) {
            messageLabel.setBackground(Color.BLUE);
            messageLabel.setForeground(Color.WHITE);
            setAlignmentX(RIGHT_ALIGNMENT);
        } else {
            messageLabel.setBackground(Color.GRAY);
            messageLabel.setForeground(Color.WHITE);
            setAlignmentX(LEFT_ALIGNMENT);
        }
        add(messageLabel);
    }
}
