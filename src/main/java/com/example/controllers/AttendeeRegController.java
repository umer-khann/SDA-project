package com.example.controllers;

import com.example.oopfiles.Attendee;
import com.example.oopfiles.GeneralAttendee;
import com.example.oopfiles.VipAttendee;
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

public class AttendeeRegController implements Initializable {
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
    public void signUpButtonOnAction(javafx.event.ActionEvent e) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String contact = contactField.getText();
        String email = emailField.getText();

        // Check for empty fields
        if (username.isBlank() || password.isBlank() || contact.isBlank() || email.isBlank()) {
            signUpmessagelabel.setText("Please input full details!");
            return;
        }

        // Validate email format using a regex pattern
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            signUpmessagelabel.setText("Invalid email format!");
            return;
        }

        // Validate password strength (e.g., minimum length of 6, contains at least one number)
//        if (!password.matches(".*\\d.*") || !password.matches(".*[A-Za-z].*")) {
//            signUpmessagelabel.setText("Password must be at least 6 characters long and contain both letters and numbers!");
//            return;
//        }

        // Validate contact details (e.g., should be a valid phone number format)
        if (!contact.matches("\\d{10}")) {  // Assuming phone number should be 10 digits long
            signUpmessagelabel.setText("Invalid contact number! Must be 10 digits.");
            return;
        }

        // Create a new Attendee object based on choice
        Attendee newAttendee;
        if (myChoiceBox.getValue().toString().equals(choices[0])) {
            newAttendee = new GeneralAttendee();
        } else if (myChoiceBox.getValue().toString().equals(choices[1])) {
            newAttendee = new VipAttendee();
        } else {
            newAttendee = null;
        }

        // Set the attendee details
        newAttendee.setName(nameField.getText());
        newAttendee.setEmail(emailField.getText());
        newAttendee.setContactDetails(contactField.getText());
        newAttendee.setUserName(username);
        newAttendee.setPassword(password);
        // Validate registration (check uniqueness of username/email)
        if (!newAttendee.isValidUserName()) {
            signUpmessagelabel.setText("UserName Exists");
            return;  // Don't proceed if validation fails
        }
        if (!newAttendee.isValidEmail()) {
            signUpmessagelabel.setText("Email exists");
            return;  // Don't proceed if validation fails
        }

        // Register the attendee
        newAttendee.registerAttendee();

        // Load the next page
        loadPage2("Attendee-main-page.fxml", e);
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
    private void loadPage2(String fxmlFile, ActionEvent event) throws IOException {
        // Load the FXML file

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        AnchorPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane

        // Get the stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(root));
        stage.setTitle("");
        // Show the stage
        stage.show();
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

    public void backButtonOnAction(javafx.event.ActionEvent e) throws IOException {
        loadPage("home-page.fxml", e);
    }
}
