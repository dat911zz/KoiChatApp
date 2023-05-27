/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.model;

import com.google.firebase.database.Exclude;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author datcy
 */
public class ChatData {
    private HashMap<String, ChatRoom> chatrooms;

    public HashMap<String, ChatRoom> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(HashMap<String, ChatRoom> chatrooms) {
        this.chatrooms = chatrooms;
    }
    
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("chatrooms", chatrooms);
        return result;
    }
}
