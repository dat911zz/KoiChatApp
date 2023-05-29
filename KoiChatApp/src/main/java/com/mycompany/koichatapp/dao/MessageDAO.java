/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.dao;

import com.google.firebase.database.DatabaseReference;
import com.mycompany.koichatapp.model.Message;
import java.util.Map;

/**
 *
 * @author datcy
 */
public class MessageDAO {

    private DatabaseReference ref;

    public DatabaseReference getRef() {
        return ref;
    }

    public void setRef(DatabaseReference ref) {
        this.ref = ref;
    }

    private static MessageDAO instance;

    private MessageDAO() {}
    public static MessageDAO getInstance(){
        if (instance == null) {
            instance = new MessageDAO();
        }
        return instance;
    }
    public void saveMessage(String chatRoomId, Message message, DatabaseReference.CompletionListener completionListener) {
        DatabaseReference messagesRef = ref.child("chatrooms").child(chatRoomId).child("messages");
        DatabaseReference newMessRef = messagesRef.push();
        String id = newMessRef.getKey();
        messagesRef.child(id).setValue(message, completionListener);
    }

    public void updateMessage(String chatRoomId, String messageId, Map<String, Object> updates, DatabaseReference.CompletionListener completionListener) {
        DatabaseReference messageRef = ref.child("chatrooms").child(chatRoomId).child("messages").child(messageId);
        messageRef.updateChildren(updates, completionListener);
    }

    public void deleteMessage(String chatRoomId, String messageId, DatabaseReference.CompletionListener completionListener) {
        DatabaseReference messageRef = ref.child("chatrooms").child(chatRoomId).child("messages").child(messageId);
        messageRef.removeValue(completionListener);
    }
}
