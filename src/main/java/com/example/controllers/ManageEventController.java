package com.example.controllers;

import com.example.JDBC.EventDBController;
import com.example.oopfiles.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Date;

public class ManageEventController {


    @FXML
    private Label welcomeText;

    @FXML
    private TextField eventid; // TextField for Event ID

    @FXML
    private TextField name; // TextField for Event Name

    @FXML
    private DatePicker date; // DatePicker for Event Date


    @FXML
    private Button createEventButton;

    @FXML
    private Button deleteEventButton;

    @FXML
    private Button updateEventButton;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, Integer> colEventID;

    @FXML
    private TableColumn<Event, String> colEventName;

    @FXML
    private TableColumn<Event, String> colEventType;

    @FXML
    private TableColumn<Event, String> colEventDate;

    private int currentOrganizerid;  // ID of the current event organizer

    // Setter method to set the current EventOrganizer's ID
    public void setEventOrgID(int eventOrganizerid) {
        this.currentOrganizerid = eventOrganizerid;
    }

    // Initialize the table view and load event data based on the organizer's ID
    @FXML
    public void setUpdateEventButton() {
        // Ensure currentOrganizerid is set
        if (currentOrganizerid <= 0) {
            showAlert("Error", "No event organizer assigned. Please log in first.");
            return;
        }

        // Link columns to Event model properties
        initializeColumn(colEventID, "eventID");
        initializeColumn(colEventName, "eventName");
        initializeColumn(colEventType, "eventType");
        initializeColumn(colEventDate, "eventDate");

        // Load and filter data from the database based on the organizer's ID
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        eventList = Event.initializeTableForOrganizer(eventList, currentOrganizerid);  // Filter events by organizer ID
        eventTable.setItems(eventList);
    }

    // Helper method for initializing table columns
    private <T> void initializeColumn(TableColumn<Event, T> column, String property) {
        column.setCellValueFactory(new PropertyValueFactory<>(property));
    }

    public void initialize() {}

    // Reset the table when necessary (e.g., after event creation, deletion, or update)
    public void resetTable() {
        initialize();  // Reload the table with updated data
    }

    // Method to load new scenes (pages)
    private void loadPage(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        BorderPane root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Method to handle event deletion
    @FXML
    public void deleteEventButtonOnAction(ActionEvent actionEvent) {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            // Check if the selected event belongs to the current event organizer
            if (selectedEvent.getOrganizerID() == currentOrganizerid) {
                int eventID = selectedEvent.getEventID();
                boolean exists = Event.checkEvent(eventID);  // Check if the event exists
                if (exists) {
                    Event.deleteEvent(eventID);
                    showAlert("Success", "Event ID " + eventID + " successfully deleted!");
                    resetTable();  // Reset the table after deletion
                } else {
                    showAlert("Error", "Event not found or already deleted.");
                }
            } else {
                showAlert("Error", "You can only delete events that you manage.");
            }
        } else {
            showAlert("Error", "Please select an event to delete.");
        }
    }

    // Method to handle event update directly in the current controller
    @FXML
    public void updateEventButtonOnAction(ActionEvent actionEvent) {
        // Retrieve the input values from the GUI
        String eventIDText = eventid.getText();
        String newName = name.getText();
        Date newDate = null;
        if (date.getValue() != null) {
            newDate = java.sql.Date.valueOf(date.getValue()); // Convert JavaFX DatePicker value to java.sql.Date
        }

        // Validate inputs (basic checks)
        if (eventIDText.isEmpty()) {
            showAlert("Error", "Event ID must be provided.");
            return;
        }

        try {
            int eventID = Integer.parseInt(eventIDText); // Convert eventID to integer

            // Get the list of Event IDs currently in the TableView
            ObservableList<Event> eventList = eventTable.getItems();
            boolean eventExistsInTable = eventList.stream().anyMatch(event -> event.getEventID() == eventID);

            // Check if the entered event ID exists in the TableView
            if (!eventExistsInTable) {
                showAlert("Error", "Event ID " + eventID + " does not exist in the current event list.");
                return;
            }

            // Call the update function in the DB controller (handles updating name, date, or both)
            boolean updateSuccessful = Event.updateEvent(eventID, newName.isEmpty() ? null : newName, newDate);

            // Insert notification into EventUpdateNotification table if update is successful
            if (updateSuccessful) {
                // Create the notification message
                String message = "Event details updated. ";
                if (newName != null && !newName.isEmpty()) {
                    message += "Name changed to: " + newName + ". ";
                }
                if (newDate != null) {
                    message += "Date changed to: " + newDate.toString() + ". ";
                }

                // Retrieve the Event Organizer's UserID
                int organizerUserID = currentOrganizerid;

                // Insert notification for the Event Organizer
                Event.insertEventUpdateNotification(eventID, organizerUserID, message);

                showAlert("Success", "Event details updated and notification sent to the Attendee successfully!");
            } else {
                showAlert("Failure", "Failed to update event details.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Event ID. Please enter a valid number.");
        }
    }





    // Display alert messages to the user
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
