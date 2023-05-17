/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cvt.chat.model;

import java.util.List;

/**
 *
 * @author datcy
 */
public class ChatRoom {
    private String id;
    private String name;
    private List<String> members;
    private List<String> messages;

    public ChatRoom(String id, String name, List<String> members, List<String> messages) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}

