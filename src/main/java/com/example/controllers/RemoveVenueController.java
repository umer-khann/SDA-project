package com.example.controllers;

import com.example.JDBC.VenueDBHandler;
import com.example.oopfiles.IndoorVenue;
import com.example.oopfiles.Venue;
import com.example.oopfiles.VenueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RemoveVenueController implements Initializable {
    @FXML
    public TableView<Venue> VenueTable;  // Specify Venue type for better type safety
    @FXML
    public TableColumn<Venue, Integer> colVenueId;
    @FXML
    public TableColumn<Venue, String> colVenueName;
    @FXML
    public TableColumn<Venue, String> colLocation;
    @FXML
    public TableColumn<Venue, Integer> colCapacity;
    public TextField eventid;
    @FXML
    private Button deleteButton;  // Assuming the delete button exists in FXML
    private int EVORGID;
    private Venue ven;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize columns before loading data
        initializeVenueTable();

        ven = VenueFactory.createVenue("INDOOR");
    }
    // Initialize Venue Table columns
    private void initializeVenueTable() {
        colVenueId.setCellValueFactory(new PropertyValueFactory<>("VenueId"));
        colVenueName.setCellValueFactory(new PropertyValueFactory<>("VenueName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
    }

    // Load venue data into the table
    private void loadVenueData() {
        ObservableList<Venue> venueList = FXCollections.observableArrayList();
        List<Venue> venues = ven.getAllVenues(EVORGID);  // Check if this list is not empty
        System.out.println("Fetched Venues: " + venues); // Debugging statement
        venueList.addAll(venues);
        VenueTable.setItems(venueList);
    }
    public void setEventOrgID(int eventOrgID) {
        this.EVORGID = eventOrgID;
    }

    public void display(ActionEvent e){
        loadVenueData();
    }
    public void buttonpress(ActionEvent actionEvent) {
        if (eventid.getText() != null && !eventid.getText().isEmpty()) {
            try {
                int venueID = Integer.parseInt(eventid.getText());
                boolean success = ven.deleteVenue(venueID,EVORGID);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Venue deleted successfully!");
                    loadVenueData();  // Reload venue data to update the table view
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete venue. Please check if the venue ID exists.");
                }
            } catch (NumberFormatException e) {
                // Handle case where input is not a valid number
                showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please enter a valid numeric Venue ID.");
            }
        } else {
            // Show a warning if the input field is empty
            showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter a Venue ID to delete.");
        }
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional: No header
        alert.setContentText(message);
        alert.showAndWait();
    }
}
