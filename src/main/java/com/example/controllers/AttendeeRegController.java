package com.example.controllers;

import com.example.oopfiles.*;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.oopfiles.UserFactory.*;

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
            showAlert("Input Error", "Please input full details!");
            return;
        }

        // Validate email format using a regex pattern
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert("Invalid Email", "Invalid email format!");
            return;
        }

        // Validate contact details (e.g., should be a valid phone number format)
        if (!contact.matches("\\d{10}")) {  // Assuming phone number should be 10 digits long
            showAlert("Invalid Contact Number", "Invalid contact number! Must be 10 digits.");
            return;
        }

        // Create a new Attendee object based on choice
        User newAttendee = null;
        if (myChoiceBox.getValue().toString().equalsIgnoreCase(choices[0])) {
            newAttendee = createUser("GENERAL_ATTENDEE");
        } else if (myChoiceBox.getValue().toString().equals(choices[1])) {
            newAttendee = UserFactory.createUser("VIP_ATTENDEE");
        }

        // Set the attendee details
        newAttendee.setName(nameField.getText());
        newAttendee.setEmail(emailField.getText());
        newAttendee.setContactDetails(contactField.getText());
        newAttendee.setUserName(username);
        newAttendee.setPassword(password);

        // Validate registration (check uniqueness of username/email)
        if (!newAttendee.isValidUserName()) {
            showAlert("Username Error", "Username already exists!");
            return;  // Don't proceed if validation fails
        }
        if (!newAttendee.isValidEmail()) {
            showAlert("Email Error", "Email already exists!");
            return;  // Don't proceed if validation fails
        }

        // Register the attendee
        newAttendee.registerAttendee();

        // Load the next page
        loadPage("home-page.fxml", e);
    }

    // Method to display alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);  // You can set a header text if needed
        alert.setContentText(message);
        alert.showAndWait();
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

    public void setAttendeeID(int attendeeID) {
    }
}
