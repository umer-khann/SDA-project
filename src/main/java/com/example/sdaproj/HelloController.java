package com.example.sdaproj;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;


import javax.swing.*;
import java.awt.event.ActionEvent;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label loginmessagelabel;


    @FXML
    private Button loginbutton;

    @FXML
    private TextField uname;

    @FXML
    private PasswordField upass;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void loginbuttonOnAction(javafx.event.ActionEvent e) {
        loginmessagelabel.setText("You tried to login");

        if(!uname.getText().isBlank() && !upass.getText().isBlank()){
            loginmessagelabel.setText("You tried to login");
        }
        else {
            loginmessagelabel.setText("Please input full details");
        }
    }
}