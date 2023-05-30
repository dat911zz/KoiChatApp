/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.model;

/**
 *
 * @author datcy
 */
public class User {

    public User() {
    }

    public User(String avatar, String displayname, Information information, String password, String username) {
        this.avatar = avatar;
        this.displayname = displayname;
        this.information = information;
        this.password = password;
        this.username = username;
    }
    private String avatar;
    private String displayname;
    private Information information;
    private String password;
    private String username;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }
    
    
}
