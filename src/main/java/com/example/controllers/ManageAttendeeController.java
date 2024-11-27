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

        // Validate Username field (should only contain alphanumeric characters, underscores, or hyphens if entered



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

    public void updateButtonOnAction(ActionEvent actionEvent) {
        try {
            // Retrieve input values from the fields
            String idText = IDField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String contact = contactField.getText().trim();
            String loyaltyPointsText = loyaltyPointsField.getText().trim();

            // Validate ID
            if (idText.isEmpty() || !idText.matches("\\d+")) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "ID must be a valid number.");
                return;
            }
            int attendeeID = Integer.parseInt(idText);

            // Validate contact (if provided)
            if (!contact.isEmpty() && !contact.matches("^\\d{10}$")) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Contact number must be 10 digits.");
                return;
            }

            // Validate email (if provided)
            if (!email.isEmpty() && !email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Email must be a valid email address.");
                return;
            }

            // Validate loyalty points (if provided)
            Integer loyaltyPoints = null; // Null means no update to this field
            if (!loyaltyPointsText.isEmpty()) {
                if (!loyaltyPointsText.matches("\\d+")) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Loyalty Points must be a valid number.");
                    return;
                }
                loyaltyPoints = Integer.parseInt(loyaltyPointsText);
            }
            // Track updated fields for the notification message
            StringBuilder updatedFields = new StringBuilder();
            if (!name.isEmpty()) {
                updatedFields.append("Name: ").append(name).append("\n");
            }
            if (!email.isEmpty()) {
                updatedFields.append("Email: ").append(email).append("\n");
            }
            if (!contact.isEmpty()) {
                updatedFields.append("Contact: ").append(contact).append("\n");
            }
            if (loyaltyPoints != null) {
                updatedFields.append("Loyalty Points: ").append(loyaltyPoints).append("\n");
            }

            // Call the DB function to update the attendee details
            boolean success = Attendee.updateAttendee(
                    attendeeID,
                    name.isEmpty() ? null : name, // Null for unchanged fields
                    email.isEmpty() ? null : email,
                    contact.isEmpty() ? null : contact,
                    loyaltyPoints
            );

            // Show appropriate alert based on success
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Attendee updated successfully.");
                String message = "Your account details have been updated successfully:\n" + updatedFields.toString();
                Attendee.addNotif(attendeeID, 3, message, "Account Details Updated Successfully.");
                // Optionally, clear fields or redirect the user to another page
                clearFields();  // Clear form fields (implement this if needed)
                displayButtonOnAction(null); // Refresh the table
            } else {
                showAlert(Alert.AlertType.WARNING, "Update Failed", "No attendee found with the given ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the attendee.");
        }
    }
    private void clearFields() {
        // Clear all the input fields
        IDField.clear();
        nameField.clear();
        emailField.clear();
        contactField.clear();
        loyaltyPointsField.clear();
        // Optionally reset any dropdowns or other UI elements if applicable
        // Example: attendeeTypeDropdown.setValue(null);  // Uncomment if you have a dropdown for attendee type
    }



    public void removeButtonOnAction(ActionEvent actionEvent) {
        String id = IDField.getText().trim();

        // Validate that ID is numeric
        if (id.isEmpty() || !id.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid numeric ID.");
            return;
        }

        if (Attendee.removeAttendee(Integer.parseInt(id))){
            showAlert(Alert.AlertType.INFORMATION, "Success", "Attendee successfully removed.");
            String message = "Your account has been removed.\n";
            Attendee.addNotif(Integer.parseInt(id), 3, message, "Your Account ID : "+id);
            displayButtonOnAction(null);
        }
        else{
            showAlert(Alert.AlertType.WARNING, "Not Found", "No attendee found with the given ID.");
        }
    }
}
