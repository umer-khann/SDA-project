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

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

public class EventOrganizerRegController implements Initializable {
    @FXML
    private Label signUpmessagelabel;

    @FXML

    private Button signUpButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField experienceField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

//    @FXML
//    public void signUpButtonOnAction(javafx.event.ActionEvent e) throws IOException {
//
//        if(!usernameField.getText().isBlank() && !passwordField.getText().isBlank() && !contactField.getText().isBlank()
//                && !emailField.getText().isBlank()){
//            signUpmessagelabel.setText("Account Created Successfully!");
//        }
//        else {
//            signUpmessagelabel.setText("Please input full details!");
//        }
//    }

    @FXML
    public void signUpButtonOnAction(javafx.event.ActionEvent e) throws IOException {
        // Retrieve input values from form fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        String contact = contactField.getText();
        String email = emailField.getText();
        String experience = experienceField.getText(); // Experience field

        // Check for empty fields
        if (username.isBlank() || password.isBlank() || contact.isBlank() || email.isBlank() || experience.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please input full details!");
            return;
        }

        // Validate email format using a regex pattern
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid email format!");
            return;
        }

        // Validate contact details (e.g., should be a valid phone number format)
        if (!contact.matches("\\d{10}")) { // Assuming phone number should be 10 digits long
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid contact number! Must be 10 digits.");
            return;
        }

        // Validate experience input as a numeric value
        if (!experience.matches("\\d+")) { // Check if experience is a valid integer
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid experience! Must be a numeric value.");
            return;
        }

        // Create a new EventOrganizer object using factory
        User eventOrganizer = UserFactory.createUser("EVENT_ORGANIZER");

        // Set the event organizer's details
        eventOrganizer.setName(nameField.getText());
        eventOrganizer.setEmail(emailField.getText());
        eventOrganizer.setContactDetails(contactField.getText());
        eventOrganizer.setUserName(username);
        eventOrganizer.setPassword(password);
        eventOrganizer.setExperienceLevel(Integer.parseInt(experience)); // Parse experience to integer

        // Validate registration (check uniqueness of username/email)
        if (!eventOrganizer.isValidUserName()) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Username already exists!");
            return; // Don't proceed if validation fails
        }
        if (!eventOrganizer.isValidEmail()) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Email already exists!");
            return; // Don't proceed if validation fails
        }

        // Register the event organizer
        RegisterAdapter userAdapter = new UserRegisterAdapter(eventOrganizer);
        userAdapter.register();

        // Assuming 'eventOrganizer' is an instance of a class handling database interactions
        int eventOrganizerID = eventOrganizer.getID(username); // Fetch the Event Organizer's ID using the username

        // Construct the message with the username and password
        String message = "Account created successfully. Username: " + username + ", Password: " + password;

        // Add the notification
        eventOrganizer.addNotification(eventOrganizerID, 2, message, "Account Registration");

        // Show success dialog
        showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Your account has been created successfully!");

        // Optionally, clear fields or redirect the user to another page
        clearFields(); // Clear form fields (implement this if needed)
        // loadPage2("EventOrganizer-main-page.fxml", e); // Navigate to the next page
    }

    // Utility method to display alert dialogs
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Optional: A helper method to clear input fields
    private void clearFields() {
        nameField.clear();
        usernameField.clear();
        passwordField.clear();
        contactField.clear();
        emailField.clear();
        experienceField.clear();
        signUpmessagelabel.setText("");
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
