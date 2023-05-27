/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.koichatapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.internal.NonNull;
import cvt.chat.component.ChatBox;
import cvt.chat.model.ChatRoom;
import cvt.chat.model.ModelMessage;
import cvt.chat.swing.ChatEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.GoogleMaterialIcon;
import javaswingdev.GradientType;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author CVT
 */
public class ChatUI extends javax.swing.JFrame {

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
    private DatabaseReference userRef;
    private DatabaseReference chatroomRef;
    private List<ChatRoom> chatRoomList;
    private String currentUserName = "vungodat";
    private String currentRoom = "";
    private boolean isFirstTime = true;
    Icon icon = new ImageIcon(getClass().getClassLoader().getResource("./Imgs/27.png"));
    Icon icon2 = new ImageIcon(getClass().getClassLoader().getResource("./Imgs/33.png"));

    /**
     * Creates new form ChatUI
     */
    public ChatUI() {
        initComponents();
        initFirebase();
//        loadSideBar();
        load();
        chatAreaCur.addChatEvent(new ChatEvent() {
            @Override
            public void mousePressedSendButton(ActionEvent evt) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void mousePressedFileButton(ActionEvent evt) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void keyTyped(KeyEvent evt) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    private void loadSideBar() {

        String date = df.format(new Date());

        this.sideBarMain.addGroupChat(new ModelMessage(icon, "CVT", date, "Châu Thịnh"));
        this.sideBarMain.addGroupChat(new ModelMessage(icon2, "VD", date, "Vũ Đạt"));
        this.sideBarMain.addGroupChat(new ModelMessage(icon, "CVT", date, "Châu Thịnh"));
        this.sideBarMain.addGroupChat(new ModelMessage(icon2, "VD", date, "Vũ Đạt"));
        this.sideBarMain.addGroupChat(new ModelMessage(icon, "CVT", date, "Châu Thịnh"));
    }

    private void load() {

        // Retrieve chat rooms from Firebase database
        chatroomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear chat room list model
                chatAreaCur.clearChatBox();
                
                List<ChatRoom> chatRooms = new ArrayList<>();
                String[] roomName = new String[1];
                String date = df.format(new Date());

                for (DataSnapshot chatRoomSnapshot : dataSnapshot.getChildren()) {
                    String roomNameTmp = chatRoomSnapshot.child("roomname").getValue(String.class);
                    if (roomNameTmp.equals("")) {
                        for (DataSnapshot romDataSnapshot : chatRoomSnapshot.child("members").getChildren()) {
                            String membName = romDataSnapshot.getValue(String.class);
                            if (!membName.equals(currentUserName)) {
                                roomNameTmp = membName;
                            }
                        }
                    }
                    sideBarMain.addGroupChat(new ModelMessage(
                            icon, 
                            "Room", 
                            date, 
                            roomNameTmp
                    ));

                    if (isFirstTime || currentRoom.equals(roomNameTmp)) {
                        currentRoom = roomNameTmp;
                        roomName[0] = roomNameTmp;
                        if (roomName[0].equals("")) {
                            chatRoomSnapshot.child("members").getChildren().forEach(memb -> {
                                String membName = memb.getValue(String.class);
                                if (!membName.equals(currentUserName)) {
                                    roomName[0] = membName;
                                }
                            });
                        }
                        chatAreaCur.setTitle(roomName[0]);
                        System.out.println();
                        chatRoomSnapshot.child("messages").getChildren().forEach(mess -> {
                            String userName = mess.child("username").getValue(String.class);
                            String messText = mess.child("message").getValue(String.class);
                            System.out.println("-- " + userName);
                            System.out.println("-- " + messText);
                            String message = chatAreaCur.getText().trim();
                            if (userName.equals(currentUserName)) {
                                chatAreaCur.addChatBox(new ModelMessage(icon, userName, date, messText), ChatBox.BoxType.RIGHT);
                            } else {
                                chatAreaCur.addChatBox(new ModelMessage(icon, userName, date, messText), ChatBox.BoxType.LEFT);
                            }
//                        chatAreaCur.addChatBox(new ModelMessage(icon2, "Bạn là ai", date, "Ăn nói xà lơ"), ChatBox.BoxType.LEFT);
                            chatAreaCur.clearTextAndGrabFocus();
                        });
                    }
                    if (isFirstTime) {
                        isFirstTime = false;
                    }
//                    chatRooms.add(chatRoom);
                }

//                // Sort chat rooms by name
//                Collections.sort(chatRooms, new Comparator<ChatRoom>() {
//                    @Override
//                    public int compare(ChatRoom o1, ChatRoom o2) {
//                        return o1.getName().compareToIgnoreCase(o2.getName());
//                    }
//                });
//                String date = df.format(new Date());
//
//                // Add chat rooms to list model
//                for (ChatRoom chatRoom : chatRooms) {
//                    chatRoomList.add(chatRoom);
//                    sideBarMain.addChatBox(new ModelMessage(icon, "CVT", date, "Châu Thịnh"));
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                throw databaseError.toException();
            }
        });
    }

//    public void listenDataChange() {
//        chatroomRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot ds) {
//                chatAreaCur.clearChatBox();
//                // Loop through all the child nodes of the "messages" node
//                for (DataSnapshot messageSnapshot : ds.getChildren()) {
//                    // Get the message data from the snapshot
//                    String username = messageSnapshot.child("username").getValue(String.class);
//                    String message = messageSnapshot.child("message").getValue(String.class);
//                    String timestamp = messageSnapshot.getKey();
//                    Icon icon = new ImageIcon(getClass().getResource("\\Imgs\\27.png"));
//                    String name = "Tôi là ai";
//                    String date = df.format(new Date());
//                    String message = chatAreaCur.getText().trim();
//                    chatAreaCur.addChatBox(new ModelMessage(icon, name, date, message), ChatBox.BoxType.RIGHT);
//                    chatAreaCur.addChatBox(new ModelMessage(new ImageIcon(getClass().getResource("\\Imgs\\33.png")), "Bạn là ai", df.format(new Date()), "Ăn nói xà lơ"), ChatBox.BoxType.LEFT);
//                    chatAreaCur.clearTextAndGrabFocus();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError de) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//        });
//    }
    public void initFirebase() {
        FileInputStream serviceAccount;
        try {
            URL url = getClass().getClassLoader().getResource("./config/chatappjavaswing-firebase-adminsdk-jr0kq-328fbe1af0.json");
            System.out.print(url.getFile());
            serviceAccount = new FileInputStream(url.getFile());
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setConnectTimeout(10000)
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://chatappjavaswing-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            chatroomRef = FirebaseDatabase.getInstance().getReference("chatrooms");
            userRef = FirebaseDatabase.getInstance().getReference("users");
        } catch (Exception ex) {
            Logger.getLogger(ChatCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundCur = new cvt.chat.swing.Background();
        sideBarMain = new cvt.chat.component.SideBar();
        chatAreaCur = new cvt.chat.component.ChatArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backgroundCur.setBackground(new java.awt.Color(255, 255, 255));
        backgroundCur.setForeground(new java.awt.Color(255, 255, 255));
        backgroundCur.setRequestFocusEnabled(false);
        backgroundCur.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideBarMain.setFocusable(false);
        backgroundCur.add(sideBarMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 620));
        backgroundCur.add(chatAreaCur, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 600, 620));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundCur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundCur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private cvt.chat.swing.Background backgroundCur;
    private cvt.chat.component.ChatArea chatAreaCur;
    private cvt.chat.component.SideBar sideBarMain;
    // End of variables declaration//GEN-END:variables
}
