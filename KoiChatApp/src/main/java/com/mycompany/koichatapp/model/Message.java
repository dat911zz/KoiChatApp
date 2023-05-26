/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.model;

import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author datcy
 */
public class Message {
    private String text;
    private long timestamp;
    private String username;

    public Message() {
    }

    public Message(String text, long timestamp, String username) {
        this.text = text;
        this.timestamp = timestamp;
        this.username = username;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("text", text);
        result.put("username", username);
        return result;
    }   
}