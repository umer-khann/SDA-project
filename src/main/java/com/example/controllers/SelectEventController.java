package com.example.controllers;

import com.example.oopfiles.Event;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


import javax.swing.*;
import java.io.IOException;

public class SelectEventController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label loginmessagelabel;

    private int EventOrgID;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, Integer> colEventID;

    @FXML
    private TableColumn<Event, String> colEventName;

    @FXML
    private TableColumn<Event, Float> colBudget;

    @FXML
    private TableColumn<Event, String> colEventType;

    @FXML
    private Button loginbutton;

    @FXML
    private TextField eventIDField;

    @FXML
    private Button applychanges;

    private Label eventLabel;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void updateTableButtonOnAction(ActionEvent event) {
        // Link columns to Event model properties
        colEventID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        colEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colBudget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        colEventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));

        // Load data from the database
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        eventTable.setItems(Event.intializeTable(eventList, EventOrgID));
    }

    public void buttonpress(ActionEvent e) throws IOException {
        // Get the value from the eventIDField
        String eventIDInput = eventIDField.getText().trim();

        // Validate the input
        if (eventIDInput.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Event ID field cannot be empty.");
            return; // Stop execution if validation fails
        }

        if (!eventIDInput.matches("\\d+")) { // Check if the input is numeric
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Event ID must be a numeric value.");
            return; // Stop execution if validation fails
        }

        boolean exists = Event.checkEvent(Integer.parseInt(eventIDInput), EventOrgID); // Static method call

        // Show success or error based on the allocation result
        if (exists) {
            AnchorPane parentPane = (AnchorPane) applychanges.getParent();
            Stage stage = (Stage) applychanges.getScene().getWindow();

            // Pass event ID to the next scene
            loadPage("manage-attendee.fxml", e, parentPane, Integer.parseInt(eventIDInput));
        } else {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Event ID does not exist.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional: Set to null to remove the header
        alert.setContentText(message);
        alert.showAndWait(); // Waits for the user to close the alert before continuing
    }

    // Method to load a new FXML into the content area
    private void loadPage(String fxml, ActionEvent e, AnchorPane contentArea, int eventID) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setToX(0);
        slide.play();

        contentArea.setVisible(true);
        contentArea.setPrefWidth(730);
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxml));
            Parent pane = loader.load();

            // Get the controller for manage-attendee.fxml
            ManageAttendeeController manageAttendeeController = loader.getController();
            manageAttendeeController.setEventID(eventID); // Pass the event ID to the next controller

            // Clear the current content and set the new content
            contentArea.getChildren().clear(); // Clear any existing children
            contentArea.getChildren().add(pane); // Add the new FXML content as a child
        } catch (IOException e1) {
            e1.printStackTrace(); // Log the error if loading fails
        }
    }

    public void setEventOrgID(int eventOrgID) {
        EventOrgID = eventOrgID;
    }
}
