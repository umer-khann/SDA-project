package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

public class EventOrganizerRegController implements Initializable {
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



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    public void signUpButtonOnAction(javafx.event.ActionEvent e) throws IOException {

        if(!usernameField.getText().isBlank() && !passwordField.getText().isBlank() && !contactField.getText().isBlank()
                && !emailField.getText().isBlank()){
            signUpmessagelabel.setText("Account Created Successfully!");
        }
        else {
            signUpmessagelabel.setText("Please input full details!");
        }
    }


    private void loadPage(String fxmlFile, ActionEvent event) throws IOException {
        // Load the FXML file

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        BorderPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane

        // Get the stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(root));
        stage.setTitle("");

        // Show the stage
        stage.show();
    }


    public void backButtonOnAction(javafx.event.ActionEvent e) throws IOException {
        loadPage("home-page.fxml", e);
    }
}
