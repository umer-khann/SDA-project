package com.example.controllers;

import com.example.oopfiles.IndoorVenue;
import com.example.oopfiles.OutdoorVenue;
import com.example.oopfiles.Venue;
import com.example.oopfiles.VenueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewVenueController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField capacityfield;
    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private TextField weatherField;
    @FXML
    private TextField additionalCapacityField;

    @FXML
    private TextField roomField;
    @FXML
    private TextField floorField;

    Venue venue;

    @FXML
    private Label weatherLabel;
    @FXML
    private Label additionalCapacityLabel;
    @FXML
    private Label roomLabel;
    @FXML
    private Label floorLabel;

    private String[] choices = {"Indoor", "Outdoor"};
    private String choice;
    private int evOrgID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myChoiceBox.getItems().addAll(choices);
        myChoiceBox.setOnAction(this::getChoice);
    }

    public void getChoice(ActionEvent event) {
        choice = myChoiceBox.getValue();

        // Show or hide fields based on the selected venue type
        if ("Outdoor".equals(choice)) {
            showOutdoorFields();
        } else if ("Indoor".equals(choice)) {
            showIndoorFields();
        }
    }

    private void showOutdoorFields() {
        // Show fields for outdoor venue
        weatherLabel.setVisible(true);
        weatherField.setVisible(true);
        weatherLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        additionalCapacityLabel.setVisible(true);
        additionalCapacityField.setVisible(true);
        additionalCapacityLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));


        // Hide indoor venue fields
        roomLabel.setVisible(false);
        roomField.setVisible(false);
        floorLabel.setVisible(false);
        floorField.setVisible(false);
    }

    private void showIndoorFields() {
        // Show fields for indoor venue
        roomLabel.setVisible(true);
        roomField.setVisible(true);
        floorLabel.setVisible(true);
        floorField.setVisible(true);
        roomLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        floorLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));



        // Hide outdoor venue fields
        weatherLabel.setVisible(false);
        weatherField.setVisible(false);
        additionalCapacityLabel.setVisible(false);
        additionalCapacityField.setVisible(false);
    }

    public void AddVenueOnAction(ActionEvent actionEvent) {
        // Step 1: Retrieve input values
        String name = nameField.getText();
        String location = locationField.getText();
        String capacityStr = capacityfield.getText();
        Venue venue;

        // Validate the string fields: Ensure they are not numbers
        if (isNumeric(name) || isNumeric(location)) {
            showAlert("Error", "Name and Location must be valid strings, not numbers!");
            return;
        }

        // Validate inputs for capacity
        if (name.isBlank() || location.isBlank() || capacityStr.isBlank()) {
            showAlert("Error", "Please fill in all required fields!");
            return;
        }

        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr); // Parse capacity to integer
        } catch (NumberFormatException e) {
            showAlert("Error", "Capacity must be a valid number!");
            return;
        }

        // Step 2: Check the venue type and handle accordingly
        if ("Outdoor".equals(choice)) {
            // Get additional fields for outdoor venue
            String weather = weatherField.getText();
            String additionalCapacityStr = additionalCapacityField.getText();

            // Validate the weather field: Ensure it's a string
            if (weather.isBlank() || isNumeric(weather)) {
                showAlert("Error", "Weather description must be a valid string, not a number!");
                return;
            }

            // Validate additional capacity: Ensure it's numeric
            if (additionalCapacityStr.isBlank()) {
                showAlert("Error", "Additional Capacity is required for Outdoor Venue!");
                return;
            }

            int additionalCapacity;
            try {
                additionalCapacity = Integer.parseInt(additionalCapacityStr);
            } catch (NumberFormatException e) {
                showAlert("Error", "Additional Capacity must be a valid number!");
                return;
            }

            // Step 3: Create Outdoor Venue
            venue= VenueFactory.createVenue("OUTDOOR",name, location, capacity, weather, additionalCapacity);
            // Call addVenue function to save the venue
            venue.addVenue(evOrgID); // Calls the addVenue method in OutdoorVenue

        } else if ("Indoor".equals(choice)) {
            // Get indoor venue-specific fields
            String room = roomField.getText();
            String floorStr = floorField.getText();

            // Validate room field: Ensure it's a string
            if (room.isBlank() || isNumeric(room)) {
                showAlert("Error", "Room description must be a valid string, not a number!");
                return;
            }

            // Validate floor: Ensure it's numeric
            if (floorStr.isBlank()) {
                showAlert("Error", "Floor is required for Indoor Venue!");
                return;
            }

            int floor;
            try {
                floor = Integer.parseInt(floorStr);
            } catch (NumberFormatException e) {
                showAlert("Error", "Floor must be a valid number!");
                return;
            }

            // Step 4: Create Indoor Venue
            venue = VenueFactory.createVenue("INDOOR",name, location, capacity, room, floor);
            // Call addVenue function to save the venue
            venue.addVenue(evOrgID); // Calls the addVenue method in IndoorVenue

        } else {
            showAlert("Error", "Please select a valid venue type!");
            return;
        }

        // Step 5: Show success message and clear fields
        showAlert("Success", "Venue added successfully!");
        clearFields();
    }

    // Method to clear all input fields
    private void clearFields() {
        nameField.clear();
        locationField.clear();
        capacityfield.clear();
        if (weatherField != null) weatherField.clear();
        if (additionalCapacityField != null) additionalCapacityField.clear();
        if (roomField != null) roomField.clear();
        if (floorField != null) floorField.clear();
    }



    public void showAlert(String title, String message) {
        // Create an alert with AlertType.NONE to avoid the default error icon
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);

        // Set the title of the alert
        alert.setTitle(title);

        // Optionally set a custom icon or no icon at all
        // alert.setGraphic(null);  // Uncomment if you don't want any icon

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }




    // Helper method to check if a string is numeric (to validate if it's a valid string)
    private boolean isNumeric(String str) {
        try {
            // Attempt to parse the string as a number
            Double.parseDouble(str);
            return true; // It's a number
        } catch (NumberFormatException e) {
            return false; // It's not a number
        }
    }
    public void setEventOrgID(int eventOrgID) {
        this.evOrgID = eventOrgID;
    }

}




