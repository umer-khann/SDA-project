package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageAttendeeController implements Initializable {
    @FXML
    private Label signUpmessagelabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ChoiceBox<String> myChoiceBox;

    private String[] choices = {"General","VIP"};

    private String choice;


    @FXML
    public void signUpButtonOnAction(javafx.event.ActionEvent e) {
        signUpmessagelabel.setText("You tried to login");

        if(!usernameField.getText().isBlank() && !passwordField.getText().isBlank() && !contactField.getText().isBlank()
                && !emailField.getText().isBlank()){
            signUpmessagelabel.setText("You tried to login");
        }
        else {
            signUpmessagelabel.setText("Please input full details!");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        myChoiceBox.getItems().addAll(choices);
        myChoiceBox.setOnAction(this::getChoice);
    }
    public void getChoice(javafx.event.ActionEvent e){
        choice=myChoiceBox.getValue();
        System.out.println(choice);
    }

}
