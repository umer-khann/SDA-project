package com.example.controllers;

import com.example.oopfiles.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;

public class HandleEventBudgetController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label loginmessagelabel;

    private int EventOrgID;
    @FXML
    private Button allocateBudgetButton;

    @FXML
    private TextField eventIDField;

    @FXML
    private TextField eventBudgetField;

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


    public void updateTableButtonOnAction(ActionEvent event){
        // Link columns to Event model properties

        colEventID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        colEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colBudget.setCellValueFactory(new PropertyValueFactory<>("budget"));
        colEventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));

        // Load data from the database
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        System.out.println(EventOrgID+" in.");
        eventTable.setItems(Event.intializeTable(eventList,EventOrgID));
    }
    public void initialize() {
    }

    public void resetTable(){
        ActionEvent ev=new ActionEvent();
        updateTableButtonOnAction(ev);
    }


    private void loadPage(String fxmlFile, ActionEvent event) throws IOException {
        // Load the FXML file

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        BorderPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane

        // Get the stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(root));

        // Show the stage
        stage.show();
    }


    public void allocateBudgetButtonOnAction(ActionEvent actionEvent) {
        // Retrieve input values from the fields
        String eventIDStr = eventIDField.getText();
        String eventBudgetStr = eventBudgetField.getText();

        // Validate event ID: Ensure it's numeric and not blank
        if (eventIDStr.isBlank() || !isNumeric(eventIDStr)) {
            showAlert("Error", "Event ID must be a valid number!");
            return;
        }

        // Validate event budget: Ensure it's numeric and not blank
        if (eventBudgetStr.isBlank() || !isNumeric(eventBudgetStr)) {
            showAlert("Error", "Event Budget must be a valid number!");
            return;
        }

        // Convert validated input to appropriate types
        int eventID = Integer.parseInt(eventIDStr);
        float eventBudget = Float.parseFloat(eventBudgetStr);
        if(eventBudget < 0) {
            showAlert("Error", "Event Budget must be positive!");
            return;
        }
        // Use the static method from Event to check the event existence
        boolean exists = Event.checkEvent(eventID, EventOrgID); // Static method call

        // Show success or error based on the allocation result
        if (exists) {
            Event.updateBudget(eventID,eventBudget);
            showAlert("Success", "Budget successfully allocated to Event ID " + eventID + "!");
            resetTable();
            clearFields(); // Clear the input fields after success
        } else {
            showAlert("Error", "Failed to allocate budget. Please check the Event ID.");
        }
    }


    // Utility method to check if a string is numeric
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);// Can parse integers and decimals
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    // Utility method to clear input fields
    private void clearFields() {
        eventIDField.clear();
        eventBudgetField.clear();
    }

    // Utility method to show alerts
    private void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }


    public void setEventOrgID(int eventOrgID) {
        EventOrgID=eventOrgID;
        System.out.println(EventOrgID+" here.");
    }
}