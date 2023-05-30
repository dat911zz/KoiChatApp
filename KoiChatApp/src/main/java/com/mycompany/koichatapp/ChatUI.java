/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.koichatapp;

import com.mycompany.koichatapp.core.ChatCore;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.internal.NonNull;
import com.google.gson.Gson;
import com.mycompany.koichatapp.core.BaseAPIServices;
import com.mycompany.koichatapp.core.ChatRoomFrm;
import com.mycompany.koichatapp.dao.ChatRoomDAO;
import com.mycompany.koichatapp.dao.MessageDAO;
import cvt.chat.component.ChatBox;
import com.mycompany.koichatapp.model.ChatData;
import com.mycompany.koichatapp.model.ChatGPTDTO;
import com.mycompany.koichatapp.model.ChatRoom;
import com.mycompany.koichatapp.model.Message;
import com.mycompany.koichatapp.model.User;
import com.mycompany.koichatapp.model.UserData;
import cvt.chat.model.ModelMessage;
import cvt.chat.swing.ChatEvent;
import cvt.chat.swing.GroupChatEvent;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.GoogleMaterialIcon;
import javaswingdev.GradientType;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author CVT
 */
public class ChatUI extends javax.swing.JFrame {

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
    ChatData chatData;
    UserData userData;
    private DatabaseReference ref;
    private String currentUserName = "";
    private String currentRoom = "";
    private boolean isFirstTime = true;
    Icon icon = new ImageIcon(getClass().getClassLoader().getResource("./Imgs/27.png"));
    Icon icon2 = new ImageIcon(getClass().getClassLoader().getResource("./Imgs/33.png"));
    Icon gptIcon;

    /**
     * Creates new form ChatUI
     */
    public ChatUI() {
        initComponents();
        try {
            URL url;
            url = new URL("https://firebasestorage.googleapis.com/v0/b/chatappjavaswing.appspot.com/o/chatgpt_logo_new.png?alt=media&token=8b6df5a5-04fa-4cc5-856f-c27598fa7647");
            gptIcon = new ImageIcon(ImageIO.read(url));
        } catch (Exception ex) {
            System.out.println("Lỗi: " + ex.getMessage());
        }

        ref = ChatCore.getInstance().getReference("");
        ChatCore.getInstance().setRef(ref);
        MessageDAO.getInstance().setRef(ref);
        ChatRoomDAO.getInstance().setRef(ref);
//        JOptionPane.showMessageDialog(rootPane, "Đang tải...");

//        loadSideBar();
        addControl();
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                ChatRoomFrm frame = new ChatRoomFrm();
//                frame.setVisible(true);
//            }
//        });
    }

    public ChatUI(String currentUser) {
        initComponents();
        try {
            URL url;
            url = new URL("https://firebasestorage.googleapis.com/v0/b/chatappjavaswing.appspot.com/o/chatgpt_logo_new.png?alt=media&token=8b6df5a5-04fa-4cc5-856f-c27598fa7647");
            gptIcon = new ImageIcon(ImageIO.read(url));
            currentUserName = currentUser;
        } catch (Exception ex) {
            System.out.println("Lỗi: " + ex.getMessage());
        }

        ref = ChatCore.getInstance().getReference("");
        ChatCore.getInstance().setRef(ref);
        MessageDAO.getInstance().setRef(ref);
        ChatRoomDAO.getInstance().setRef(ref);
//        JOptionPane.showMessageDialog(rootPane, "Đang tải...");

//        loadSideBar();
        addControl();
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                ChatRoomFrm frame = new ChatRoomFrm();
//                frame.setVisible(true);
//            }
//        });
    }

    private void loadSideBar() {

        String date = df.format(new Date());

        this.sideBarMain.addGroupChat(new ModelMessage(icon, "CVT", date, "Châu Thịnh"));
        this.sideBarMain.addGroupChat(new ModelMessage(icon2, "VD", date, "Vũ Đạt"));
        this.sideBarMain.addGroupChat(new ModelMessage(icon, "CVT", date, "Châu Thịnh"));
        this.sideBarMain.addGroupChat(new ModelMessage(icon2, "VD", date, "Vũ Đạt"));
        this.sideBarMain.addGroupChat(new ModelMessage(icon, "CVT", date, "Châu Thịnh"));
    }

    public void onChangeMessageInCurrentGr() {
        if (!currentRoom.equals("") && !currentRoom.equals("GPT")) {
            ref.child("chatrooms").child(currentRoom).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    var room = ds.getValue(ChatRoom.class);
                    if (ds.getKey().equals(currentRoom)) {
                        reloadMessages(room.getMessages());
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            });
        }
    }

    private void addControl() {
        //Set event click on Group
        sideBarMain.addGroupChatEvent(new GroupChatEvent() {
            @Override
            public void onGroupChatClick(MouseEvent event, ModelMessage message) {
                System.out.println("Clicked: " + message.getName() + " | " + message.getMessage());
                currentRoom = message.getName();
                chatAreaCur.setTitle(currentRoom);
                loadData();
            }
        });
        //Send message
        chatAreaCur.addChatEvent(new ChatEvent() {
            @Override
            public void mousePressedSendButton(ActionEvent evt) {
                if (!chatAreaCur.getText().trim().equals("") && !currentRoom.equals("")) {
                    if (currentRoom.equals("GPT")) {
                        System.out.println("Sended");
                        chatAreaCur.setTitle(currentRoom);
                        chatAreaCur.addChatBox(new ModelMessage(icon, currentUserName, df.format(new Date()), chatAreaCur.getText()), ChatBox.BoxType.RIGHT);

                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        String res = BaseAPIServices.getInstance().generateResponse(chatAreaCur.getText());
                                        ChatGPTDTO data = new Gson().fromJson(res, ChatGPTDTO.class);
                                        String response = data.choices.get(0).message.content;
                                        chatAreaCur.addChatBox(new ModelMessage(gptIcon, "GPT", df.format(new Date()), response), ChatBox.BoxType.LEFT);
                                    }
                                });
                                return null;
                            }

                            @Override
                            protected void done() {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        chatAreaCur.clearTextAndGrabFocus();
                                    }
                                });
                            }
                        };
                        executorService.execute(worker);

                    } else {
                        System.out.println("Sended");
                        String newId = (chatData.getChatrooms().get(currentRoom).getMessages().size()) + "";
                        ChatCore.getInstance().sendMessage(currentRoom, newId, new Message(chatAreaCur.getText(), System.currentTimeMillis(), currentUserName));
                        onChangeMessageInCurrentGr();

                    }

                }
            }

            @Override
            public void mousePressedFileButton(ActionEvent evt) {
            }

            @Override
            public void keyTyped(KeyEvent evt) {
            }
        });
        // Retrieve chat rooms from Firebase database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ChatRoom> chatRooms = new ArrayList<>();
                String[] roomName = new String[1];
                String date = df.format(new Date());

                chatData = dataSnapshot.getValue(ChatData.class);
                userData = dataSnapshot.getValue(UserData.class);
                fillData(chatData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                throw databaseError.toException();
            }
        });
        ref.child("chatrooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                System.out.println("Chatroom changed");
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });

    }

    public void fillData(ChatData chatData) {
        loadData();
    }

    private void loadData() {
        sideBarMain.clearChatBox();

        if (currentRoom.equals("GPT")) {
            chatAreaCur.clearChatBox();
            sideBarMain.addGroupChat(new ModelMessage(
                    gptIcon,
                    "GPT",
                    df.format(new Date()),
                    "Chat Bot"
            ), true);
        } else {
            sideBarMain.addGroupChat(new ModelMessage(
                    gptIcon,
                    "GPT",
                    df.format(new Date()),
                    "Chat Bot"
            ));
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LinkedHashMap<String, ChatRoom> sortedMap = sortChatRoom(chatData.getChatrooms());
                for (Map.Entry<String, ChatRoom> room : sortedMap.entrySet()) {
                    String date = df.format(new Date());
                    if (room.getValue().getRoomname().equals("")) {
                        for (String userName : room.getValue().getMembers()) {
                            if (!userName.equals(currentUserName)) {
                                room.getValue().setRoomname(findUserByUserName(userName).getDisplayname());
                            }
                        }
                    }
                    //Load chat groups
                    if (isUserInGr(currentUserName, room.getValue())) {
                        if (currentRoom.equals(room.getKey())) {
                            sideBarMain.addGroupChat(new ModelMessage(
                                    icon,
                                    room.getKey(),
                                    date,
                                    room.getValue().getRoomname()
                            ), true);
                        } else {
                            sideBarMain.addGroupChat(new ModelMessage(
                                    icon,
                                    room.getKey(),
                                    date,
                                    room.getValue().getRoomname()
                            ));
                        }
                    }

                    //Load messages
                    if (currentRoom.equals(room.getKey())) {
                        chatAreaCur.setTitle(room.getValue().getRoomname());
                        reloadMessages(room.getValue().getMessages());
                    }
                }
            }
        });

    }

    private void reloadMessages(HashMap<String, Message> messages) {
        chatAreaCur.clearChatBox();
        LinkedHashMap<String, Message> sortedMess = sortMess(messages);
        for (Map.Entry<String, Message> mess : sortedMess.entrySet()) {
            String userName = mess.getValue().getUsername();
            String messText = mess.getValue().getText();

            if (userName.equals(currentUserName)) {
                chatAreaCur.addChatBox(new ModelMessage(icon, findUserByUserName(userName).getDisplayname(), df.format(mess.getValue().getTimestamp()), messText), ChatBox.BoxType.RIGHT);
            } else {
                chatAreaCur.addChatBox(new ModelMessage(icon, findUserByUserName(userName).getDisplayname(), df.format(mess.getValue().getTimestamp()), messText), ChatBox.BoxType.LEFT);
            }
            chatAreaCur.clearTextAndGrabFocus();
        }
    }

    private static LinkedHashMap<String, ChatRoom> sortChatRoom(HashMap<String, ChatRoom> inputHashMap) {
        // Sort the entries based on the age attribute of Child objects
        List<Map.Entry<String, ChatRoom>> sortedEntries = new ArrayList<>(inputHashMap.entrySet());
        sortedEntries.sort(Comparator.comparingLong(entry -> entry.getValue().getLastAccess()));
        // Create a LinkedHashMap to preserve the order
        LinkedHashMap<String, ChatRoom> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, ChatRoom> entry : sortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private static LinkedHashMap<String, Message> sortMess(HashMap<String, Message> inputHashMap) {
        // Sort the entries based on the age attribute of Child objects
        List<Map.Entry<String, Message>> sortedEntries = new ArrayList<>(inputHashMap.entrySet());
        sortedEntries.sort(Comparator.comparingLong(entry -> entry.getValue().getTimestamp()));
        // Create a LinkedHashMap to preserve the order
        LinkedHashMap<String, Message> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Message> entry : sortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private User findUserByUserName(String username) {
        User user = new User();
        for (Map.Entry<String, User> u : userData.getUsers().entrySet()) {
            if (u.getValue().getUsername().equals(username)) {
                user = u.getValue();
            }
        }
        return user;
    }

    private boolean isUserInGr(String username, ChatRoom room) {
        for (String uname : room.getMembers()) {
            if (username.equals(uname)) {
                return true;
            }
        }
        return false;
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
