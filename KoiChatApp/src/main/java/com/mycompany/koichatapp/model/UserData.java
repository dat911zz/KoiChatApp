/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.model;

import java.util.HashMap;

/**
 *
 * @author datcy
 */
public class UserData {
    private HashMap<String, User> users;

    public HashMap<String, User>getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }
    
}
