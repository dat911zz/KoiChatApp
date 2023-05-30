/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.model;

/**
 *
 * @author datcy
 */
public class Information {

    public Information(String dateofbirth, String fullname, String gender) {
        this.dateofbirth = dateofbirth;
        this.fullname = fullname;
        this.gender = gender;
    }

    public Information() {
    }
    private String dateofbirth;
    private String fullname;
    private String gender;

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
}
