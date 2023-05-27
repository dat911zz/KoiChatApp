/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.model;

import com.google.firebase.database.Exclude;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author datcy
 */
public class ChatRoom {
    private String admin;
    private String avatar;
    private ArrayList<String> members;
    private HashMap<String, Message> messages;
    private String roomname;
    private long lastAccess;

    public long getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(long lastAccess) {
        this.lastAccess = lastAccess;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
    
    public HashMap<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, Message> messages) {
        this.messages = messages;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }
    
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("admin", admin);
        result.put("avatar", avatar);
        result.put("members", members);
        result.put("messages", messages);
        result.put("roomname", roomname);
        return result;
    }
}
