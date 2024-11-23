package com.example.controllers;

import com.example.oopfiles.Attendee;
import com.example.oopfiles.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageAttendeeController implements Initializable {

    private int EventID;

    private int EventOrgID;



    @FXML
    private Button signUpButton;

    @FXML
    private TextField IDField;

    @FXML
    private TextField loyaltyPointsField;

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
    private TableView<Attendee> attendeeTable;

    @FXML
    private TableColumn<Attendee, Integer> colAttendeeID;

    @FXML
    private TableColumn<Attendee, String> colAttendeeName;

    @FXML
    private TableColumn<Attendee, String> colAttendeeEmail;

    @FXML
    private TableColumn<Attendee, String> colAttendeeContact;

    @FXML
    private TableColumn<Attendee, Integer> colLoyaltyPoints;

    @FXML
    private TableColumn<Attendee, Integer> colAttendeeType;

//    @FXML
//    private ChoiceBox<String> myChoiceBox;
//
//    private String[] choices = {"General","VIP"};
//
//    private String choice;


    @FXML
    public void signUpButtonOnAction(javafx.event.ActionEvent e) {
        // Validate each field
        String id = IDField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String contact = contactField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String points=loyaltyPointsField.getText().trim();

        boolean valid = true;

        // Validate ID field (should be a positive number if entered)
        if (!id.isEmpty() && !id.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "ID must be a valid number.");
            valid = false;
        }

        if (!points.isEmpty() && !points.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Points must be a valid number.");
            valid = false;
        }

        // Validate Name field (should only contain letters and spaces if entered)
        if (!name.isEmpty() && !name.matches("[a-zA-Z\\s]+")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Name must contain only letters and spaces.");
            valid = false;
        }

        // Validate Email field (should be a valid email if entered)
        if (!email.isEmpty() && !email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Email must be a valid email address.");
            valid = false;
        }

        // Validate Contact field (should be a 10-digit phone number if entered)
        if (!contact.isEmpty() && !contact.matches("^\\d{10}$")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Contact number must be 10 digits.");
            valid = false;
        }

        // Validate Username field (should only contain alphanumeric characters, underscores, or hyphens if entered)
        if (!username.isEmpty() && !username.matches("^[a-zA-Z0-9_-]+$")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username can only contain alphanumeric characters, underscores, and hyphens.");
            valid = false;
        }

        // Validate Password field (should be at least 6 characters if entered)
        if (!password.isEmpty() && password.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password must be at least 6 characters long.");
            valid = false;
        }

        // If all fields are valid, process the sign-up logic
        if (valid) {
            System.out.println("Sign-up details are valid.");
            // You can proceed with the sign-up logic, like storing the data, etc.
            // For example:
            // processSignUp(id, name, email, contact, username, password);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
//        myChoiceBox.getItems().addAll(choices);
//        myChoiceBox.setOnAction(this::getChoice);
    }
//    public void getChoice(javafx.event.ActionEvent e){
//        choice=myChoiceBox.getValue();
//        System.out.println(choice);
//    }

    public void setEventOrgID(int eventOrgID) {
        this.EventOrgID=eventOrgID;
    }

    public void setEventID(int eventID) {
        this.EventID=eventID;
    }

    public void displayButtonOnAction(ActionEvent actionEvent) {
        // Link columns to Attendee model properties
        colAttendeeID.setCellValueFactory(new PropertyValueFactory<>("attendeeID"));
        colAttendeeName.setCellValueFactory(new PropertyValueFactory<>("attendeeName"));
        colAttendeeEmail.setCellValueFactory(new PropertyValueFactory<>("attendeeEmail"));
        colAttendeeContact.setCellValueFactory(new PropertyValueFactory<>("attendeeContact"));
        colLoyaltyPoints.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));
        colAttendeeType.setCellValueFactory(new PropertyValueFactory<>("attendeeType")); // For 'attendeeType' column

        // Fetch the list of attendees for the event from the database (or any other source)
        ObservableList<Attendee> attendeeList = FXCollections.observableArrayList();

        attendeeList = Event.displayAttendees(EventID); // Replace with actual method to get attendees

        // Set the list of attendees in the TableView
        attendeeTable.setItems(attendeeList);
    }
}
