/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author datcy
 */
public class ChatCore {
    private static ChatCore instance;
    private DatabaseReference ref;

    public void setRef(DatabaseReference ref) {
        this.ref = ref;
    }

    public DatabaseReference getRef() {
        return ref;
    }
    
    public static ChatCore getInstance(){
        if (instance == null) {
            instance = new ChatCore();
        }
        return instance;
    }

    private ChatCore() {
        FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream(
                    System.getProperty("user.dir")
                    + "/src/main/java/config/"
                    + "chatappjavaswing-firebase-adminsdk-jr0kq-328fbe1af0.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://chatappjavaswing-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            ref = FirebaseDatabase.getInstance().getReference("messages");
        } catch (Exception ex) {
            Logger.getLogger(ChatCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
