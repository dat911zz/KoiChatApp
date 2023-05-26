/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mycompany.koichatapp.model.ChatRoom;
import java.util.HashMap;

/**
 *
 * @author datcy
 */
public class ChatRoomDAO {
    private DatabaseReference ref;

    public DatabaseReference getRef() {
        return ref;
    }

    public void setRef(DatabaseReference ref) {
        this.ref = ref;
    }
    private static ChatRoomDAO instance;

    private ChatRoomDAO() {}
    public static ChatRoomDAO getInstance(){
        if (instance == null) {
            instance = new ChatRoomDAO();
        }
        return instance;
    }
    public void getChatRooms(ValueEventListener listener) {
        DatabaseReference chatRoomsRef = ref.child("chatrooms");
        chatRoomsRef.addListenerForSingleValueEvent(listener);
    }

    public void saveChatRoom(ChatRoom chatRoom, DatabaseReference.CompletionListener completionListener) {
        DatabaseReference chatRoomsRef = ref.child("chatrooms");
        String roomId = chatRoomsRef.push().getKey(); // Generate a unique key for the chat room
        chatRoomsRef.child(roomId).setValue(chatRoom, completionListener);
    }

    public void updateChatRoom(String idRoom, ChatRoom chatRoom, DatabaseReference.CompletionListener completionListener) {
        DatabaseReference chatRoomsRef = ref.child("chatrooms");
        chatRoomsRef.child(idRoom).updateChildren(chatRoom.toMap(), completionListener);
    }

    public void deleteChatRoom(String chatRoomId, DatabaseReference.CompletionListener completionListener) {
        DatabaseReference chatRoomsRef = ref.child("chatrooms");
        chatRoomsRef.child(chatRoomId).removeValue(completionListener);
    }
}
