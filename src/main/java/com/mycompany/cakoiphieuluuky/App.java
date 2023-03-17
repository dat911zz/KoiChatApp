package com.mycompany.cakoiphieuluuky;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //Khởi tạo fxml Login và setup stage
        scene = new Scene(loadFXML("Login"), 640, 400);
        stage.setScene(scene);
        stage.setTitle("Hệ thống quản trị BankNSolution");
        stage.show();
        //Gọi các thành phần view
        TextField txtName = (TextField) scene.lookup("#txtName");
        PasswordField txtPass = (PasswordField) scene.lookup("#txtPass");
        Button btnSubmit = (Button) scene.lookup("#loginBtn");
        //Bắt event thông qua code
        btnSubmit.setOnAction((event) -> {    // lambda expression
            System.out.println("Clicked!\n");
            System.out.println("Name: " + txtName.getText() + "\nPassword: " + txtPass.getText());
            if (!txtName.getText().equals("") && !txtPass.getText().equals("")) {
                try {
                    //Mở một fxml khác
                    App.setRoot("secondary");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else{
                System.out.println("Error: All field are required!");
            }
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}