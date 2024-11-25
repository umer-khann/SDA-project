package com.example.controllers;

import com.example.oopfiles.Event;
import com.example.oopfiles.Sponsorship;
import com.example.oopfiles.Venue;
import com.example.oopfiles.WorkshopEvent;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSponsershipController implements Initializable {
    @FXML
    private TableView<Event> Table;
    @FXML
    private TableColumn<Venue, String> EVID;
    @FXML
    private TableColumn<Venue, String> EVNAME;
    @FXML
    private TableColumn<Venue, String> EVDATE;
    @FXML
    private TableColumn<Venue, String> EVTYPE;
    @FXML
    private TextField SpAmount;
    @FXML
    private TextField SpName;
    @FXML
    private TextField EventID;
    @FXML
    private int evOrgID;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeEventTable();
    }

    public void setEventOrgID(int eventOrgID) {
        this.evOrgID = eventOrgID;
    }
    public void AddPushed(ActionEvent actionEvent) {
        // Retrieve input values from the form
        String sponsorshipName = SpName.getText().trim();
        String sponsorshipAmount = SpAmount.getText().trim();
        String eventId = EventID.getText().trim();

        // Validate the inputs
        if (sponsorshipName.isEmpty() || sponsorshipAmount.isEmpty() || eventId.isEmpty()) {
            // Show an alert if any of the fields are empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        // Convert sponsorship amount to the correct type (e.g., double)
        double amount;
        try {
            amount = Double.parseDouble(sponsorshipAmount);
        } catch (NumberFormatException ex) {
            // Show an alert if the amount is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid sponsorship amount.");
            alert.showAndWait();
            return;
        }

        // Convert Event ID to the correct type (int)
        int eventID;
        try {
            eventID = Integer.parseInt(eventId);
        } catch (NumberFormatException ex) {
            // Show an alert if the event ID is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid Event ID.");
            alert.showAndWait();
            return;
        }

        // Create a new Sponsorship object and set its attributes
        Sponsorship s = new Sponsorship();
        s.setSponsorName(sponsorshipName);
        s.setContributionAmount(amount);
        s.setEventID(eventID);
        s.setEvorgid(evOrgID); // Assuming evOrgID is already defined elsewhere
        boolean isInserted = s.addSponsorship();

        if (isInserted) {
            // Show a success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Sponsorship added successfully!");
            alert.showAndWait();

            // Refresh the table or update the view if necessary
            loadEventData();
        } else {
            // Show an error alert if the insertion failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add sponsorship. Please try again.");
            alert.showAndWait();
        }
    }


    private void initializeEventTable() {
        EVID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        EVNAME.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        EVTYPE.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        EVDATE.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
    }
    public void DisplayPushed(ActionEvent e){
        System.out.println(evOrgID);
        loadEventData();
    }
    // Load venue data into the table
    private void loadEventData() {
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        FXCollections.observableArrayList(Event.initializeTableForOrganizer(eventList,evOrgID));
        Table.setItems(eventList);
    }

}
