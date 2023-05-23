/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.koichatapp;

import dvn.core.ChatCore;
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.NumberFormat.Style;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author datcy
 */
public class frmMain extends javax.swing.JFrame {

    private String userName = "Ẩn danh";
    private DatabaseReference ref;

    /**
     * Creates new form frmMain
     */
    public frmMain() {
        initComponents();
        initFirebase();
        addControl();
    }

    public void setFirstMess() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("messs");
        // Generate a unique message ID from the current timestamp
        String msgId = String.valueOf(System.currentTimeMillis());

        // Create a map to represent the message data
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("username", "user1");
        messageData.put("message", "Hello, world!");
        System.out.println("add");
        // Set the value of the new message using the message ID
//        ref.child(msgId).updateChildrenAsync(messageData);
        ref.setValue(msgId, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

        });

    }

    public void initFirebase() {
        FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream("D:\\Workspace\\Java\\Netbeans\\FirebaseChat\\src\\main\\java\\config\\chatappjavaswing-firebase-adminsdk-jr0kq-328fbe1af0.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setConnectTimeout(10000)
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://chatappjavaswing-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            ref = FirebaseDatabase.getInstance().getReference("messages");
        } catch (Exception ex) {
            Logger.getLogger(ChatCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addControl() {
//        btnSend.addActionListener((e) -> {
//            setFirstMess();
//        });
        listenDataChange();
        btnSend.addActionListener((e) -> {
            if (!txtMess.getText().equals("")) {

                Map<String, Object> messageData = new HashMap<>();
                messageData.put("username", userName);
                messageData.put("message", txtMess.getText());
                String msgId = String.valueOf(System.currentTimeMillis());
                ref.child(msgId).setValue(messageData, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            System.err.println("APP: Failed to add message: " + error.getMessage());
                        } else {
                            System.err.println("APP: Success!");
                        }
                    }
                });
                txtMess.setText("");
                txtOutput.revalidate();
                txtOutput.repaint();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập tin nhắn trước khi gửi!");
            }

        });
        btnChangeName.addActionListener((e) -> {
            if (txtName.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập tên trước khi đổi!");
            } else {
                userName = txtName.getText();
                lblName.setText(userName);
                JOptionPane.showMessageDialog(rootPane, "Đã đổi tên thành công!");

                txtOutput.revalidate();
                txtOutput.repaint();

            }

        });

    }

    public void listenDataChange() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                txtOutput.setText("");
                // Loop through all the child nodes of the "messages" node
                for (DataSnapshot messageSnapshot : ds.getChildren()) {
                    // Get the message data from the snapshot
                    String username = messageSnapshot.child("username").getValue(String.class);
                    String message = messageSnapshot.child("message").getValue(String.class);
                    String timestamp = messageSnapshot.getKey();
                    addMessage(username, message, timestamp);

                }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    private void addMessage(String name, String text, String timestamp) {
        String chatInput = text;
        StyledDocument doc = txtOutput.getStyledDocument();
        javax.swing.text.Style style = txtOutput.addStyle("bold", null);
        StyleConstants.setBold(style, true);
        StyleConstants.setForeground(style, Color.BLUE);

        // Display the message in your UI
        long timestampL = Long.parseLong(timestamp);
        String formattedTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(timestampL));
        String formattedMessage = String.format("[%s] %s: %s", formattedTimestamp, name, text);
        try {
            if (name.equals(lblName.getText())) {
                // Set the text "Hello" to bold
                doc.insertString(doc.getLength(), "[" + formattedTimestamp + "] " + name + ": ", style);
                doc.insertString(doc.getLength(), text + "\n", null);
            } else {
                // Set the text "world" to normal weight
                doc.insertString(doc.getLength(), formattedMessage + "\n", null);
            }
            // Scroll to the last line of the text pane
            int pos = txtOutput.getDocument().getLength() - 1;
            txtOutput.setCaretPosition(pos);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
//        txtOutput.append(name + ": " + chatInput + "\n");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dskPane = new javax.swing.JDesktopPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMess = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        txtName = new javax.swing.JTextField();
        btnChangeName = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtOutput = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnHT = new javax.swing.JMenu();
        mnDangNhap = new javax.swing.JMenuItem();
        mnDangXuat = new javax.swing.JMenuItem();
        mnThoat = new javax.swing.JMenuItem();
        mnHuongDan = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtMess.setColumns(20);
        txtMess.setRows(5);
        jScrollPane1.setViewportView(txtMess);

        btnSend.setText("Gửi");

        btnChangeName.setText("Đổi tên");

        jLabel1.setText("Người dùng:");

        lblName.setText("Ẩn danh");

        txtOutput.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jScrollPane3.setViewportView(txtOutput);

        dskPane.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskPane.setLayer(btnSend, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskPane.setLayer(txtName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskPane.setLayer(btnChangeName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskPane.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskPane.setLayer(lblName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dskPane.setLayer(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dskPaneLayout = new javax.swing.GroupLayout(dskPane);
        dskPane.setLayout(dskPaneLayout);
        dskPaneLayout.setHorizontalGroup(
            dskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dskPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(dskPaneLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dskPaneLayout.createSequentialGroup()
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnChangeName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        dskPaneLayout.setVerticalGroup(
            dskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dskPaneLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(dskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeName)
                    .addComponent(jLabel1)
                    .addComponent(lblName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mnHT.setText("Hệ thống");

        mnDangNhap.setText("Đăng nhập");
        mnHT.add(mnDangNhap);

        mnDangXuat.setText("Đăng xuất");
        mnDangXuat.setToolTipText("");
        mnHT.add(mnDangXuat);

        mnThoat.setText("Thoát");
        mnHT.add(mnThoat);

        jMenuBar1.add(mnHT);

        mnHuongDan.setText("Hướng dẫn");
        jMenuBar1.add(mnHuongDan);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskPane)
        );

        pack();
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
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangeName;
    private javax.swing.JButton btnSend;
    private javax.swing.JDesktopPane dskPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblName;
    private javax.swing.JMenuItem mnDangNhap;
    private javax.swing.JMenuItem mnDangXuat;
    private javax.swing.JMenu mnHT;
    private javax.swing.JMenu mnHuongDan;
    private javax.swing.JMenuItem mnThoat;
    private javax.swing.JTextArea txtMess;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextPane txtOutput;
    // End of variables declaration//GEN-END:variables
}
