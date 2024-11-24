package com.example.controllers;

import com.example.JDBC.EventDBController;
import com.example.oopfiles.Event;
import com.example.oopfiles.EventOrganizer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveEventOrganizerController {

    @FXML
    private TableView<EventOrganizer> organizerTable;

    @FXML
    private TextField oldorgid;
    @FXML
    private TextField neworgid;

    @FXML
    private TableColumn<EventOrganizer, Integer> colOrganizerID;

    @FXML
    private TableColumn<EventOrganizer, String> colOrganizerName;

    @FXML
    private TableColumn<EventOrganizer, Float> colEventID;

    @FXML
    private TableColumn<EventOrganizer, String> colEventName;

    public void buttonpress(ActionEvent actionEvent) {
        // Retrieve the old and new Event Organizer IDs from the text fields
        String oldOrgIdText = oldorgid.getText();
        String newOrgIdText = neworgid.getText();

        // Check if the old Event Organizer ID is empty or null
        if (oldOrgIdText == null || oldOrgIdText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter the old organizer ID to remove.");
            return;  // Exit the method to prevent further execution
        }

        int oldOrgId;
        int newOrgId;

        // Try parsing the organizer IDs to integers
        try {
            oldOrgId = Integer.parseInt(oldOrgIdText);
            newOrgId = Integer.parseInt(newOrgIdText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter valid numeric IDs.");
            return;
        }

        // Check if the old and new IDs are the same
        if (oldOrgId == newOrgId) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "The old and new organizer IDs cannot be the same.");
            return;  // Exit the method to prevent further execution
        }

        // Get the list of event organizers from the table (or database)
        ObservableList<EventOrganizer> eventList = organizerTable.getItems();

        // Check if the old Event Organizer ID exists in the table
        boolean oldOrgExists = eventList.stream().anyMatch(org -> org.getId() == oldOrgId);

        // Check if the new Event Organizer ID exists in the table
        boolean newOrgExists = eventList.stream().anyMatch(org -> org.getId() == newOrgId);

        // Debugging line to see if the old and new organizers' existence conditions are correct
        System.out.println("Old Organizer Exists: " + oldOrgExists);
        System.out.println("New Organizer Exists: " + newOrgExists);

        // If both old and new organizers exist and the old one can be removed
        if (oldOrgExists && newOrgExists) {
            // Find the old organizer and the new organizer
            EventOrganizer oldOrg = eventList.stream().filter(org -> org.getId() == oldOrgId).findFirst().orElse(null);
            EventOrganizer newOrg = eventList.stream().filter(org -> org.getId() == newOrgId).findFirst().orElse(null);

            if (oldOrg != null && newOrg != null) {

                EventOrganizer.RemoveEventOrganizer(oldOrgId, newOrgId);  // Remove from the database (if needed)

                // Show success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "The events have been transferred and the old organizer has been removed.");
            }
        } else if (!oldOrgExists) {
            showAlert(Alert.AlertType.ERROR, "Error", "Old Event Organizer ID does not exist in the table.");
        } else if (!newOrgExists) {
            showAlert(Alert.AlertType.ERROR, "Error", "New Event Organizer ID does not exist in the table.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }





    public void Refresh(ActionEvent actionEvent) {

        // Set the cell value factories to bind to the correct properties of EventOrganizer
        colEventID.setCellValueFactory(new PropertyValueFactory<>("eventid"));
        colEventName.setCellValueFactory(new PropertyValueFactory<>("eventname"));
        colOrganizerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colOrganizerName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Load data from the database
        ObservableList<EventOrganizer> eventList = FXCollections.observableArrayList();
        organizerTable.setItems(EventOrganizer.showEvents(eventList));
    }

}
