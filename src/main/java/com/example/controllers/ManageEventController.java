package com.example.controllers;

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

public class ManageEventController {

    @FXML
    private Label welcomeText;

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
    public void initialize() {
        // Ensure currentOrganizerid is set
        if (currentOrganizerid <= 0) {
            showAlert("Error", "No event organizer assigned. Please log in first.");
            return;
        }

        // Set welcome message (assuming you have an EventOrganizer object for the name)
        welcomeText.setText("Welcome, Organizer ID: " + currentOrganizerid);

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
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null && selectedEvent.getOrganizerID() == currentOrganizerid) {
            // Open a dialog to update the event details
            openUpdateEventDialog(selectedEvent);
        } else {
            showAlert("Error", selectedEvent == null ? "Please select an event to update." : "You can only update events that you manage.");
        }
    }

    // Helper method to open the dialog for updating event details
    private void openUpdateEventDialog(Event selectedEvent) {
        // Create a dialog to update event details
        TextInputDialog dialog = new TextInputDialog(selectedEvent.getEventName());
        dialog.setTitle("Update Event");
        dialog.setHeaderText("Update the event details");
        dialog.setContentText("Event Name:");

        dialog.showAndWait().ifPresent(updatedName -> {
            // Update the event with new details
            selectedEvent.setEventName(updatedName);
            Event.updateEventDetails(selectedEvent);  // Assuming Event has an updateEvent method
            showAlert("Success", "Event successfully updated!");
            resetTable();  // Reset the table after update
        });
    }

    // Utility method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
