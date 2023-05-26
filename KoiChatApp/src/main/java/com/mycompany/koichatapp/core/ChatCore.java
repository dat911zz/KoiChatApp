/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dvn.core;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
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

    public static ChatCore getInstance() {
        if (instance == null) {
            instance = new ChatCore();
        }
        return instance;
    }

    private ChatCore() {
        FileInputStream serviceAccount;
        try {
            URL url = getClass().getClassLoader().getResource("./config/chatappjavaswing-firebase-adminsdk-jr0kq-328fbe1af0.json");
            System.out.print(url.getFile());
            serviceAccount = new FileInputStream(url.getFile());
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setConnectTimeout(10000)
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://chatappjavaswing-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception ex) {
            System.err.print(ex.getMessage());
        }
    }

    public DatabaseReference getReference(String path) {
        try {
            return FirebaseDatabase.getInstance().getReference(path);
        } catch (Exception ex) {
            System.err.print(ex.getMessage());
            return null;
        }
    }
}
