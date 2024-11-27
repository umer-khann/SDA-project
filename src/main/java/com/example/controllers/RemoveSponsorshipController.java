package com.example.controllers;

import com.example.oopfiles.*;
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

public class RemoveSponsorshipController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label loginmessagelabel;

    private Sponsorship sponsorship;
    @FXML
    private Button loginbutton;


    @FXML
    private TextField eventIDField;

    @FXML
    private TextField sponsorIDField;

    @FXML
    private TableView<Sponsorship> sponsorshipTable;

    @FXML
    private TableColumn<Sponsorship, Integer> colEventID;

    @FXML
    private TableColumn<Sponsorship, String> colEventName;

    @FXML
    private TableColumn<Sponsorship, Integer> colSponsorID;

    @FXML
    private TableColumn<Sponsorship, String> colSponsorName;

    @FXML
    private TableColumn<Sponsorship, Double> colContributionAmount;

    private int EventOrgID;
    User admin;
    User eventOrganizer;



    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void loginbuttonOnAction(javafx.event.ActionEvent e) throws IOException {
        // Load the new FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/hello-view.fxml"));

        // Since the root of hello-view.fxml is a BorderPane, cast to BorderPane
        BorderPane root = fxmlLoader.load();

        // Create a new scene with the loaded FXML
        Scene scene = new Scene(root);

        // Get the current stage (window) using the button that triggered the action
        javafx.stage.Stage stage = (javafx.stage.Stage) loginbutton.getScene().getWindow();

        // Set the scene to the stage
        stage.setScene(scene);

        // Optionally, you can give the new window a title
        stage.setTitle("");

        // Show the new scene and close the old one
        stage.show();
    }
    public void backButtonOnAction(javafx.event.ActionEvent e) throws IOException {
        loadPage("home-page.fxml", e);
    }
    private void loadPage(String fxmlFile, ActionEvent event) throws IOException {
        // Load the FXML file

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        BorderPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane

        // Get the stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(root));
        stage.setTitle("");

        // Show the stage
        stage.show();
    }




    public void removeSponsorButtonOnAction(ActionEvent actionEvent) {
        String eventID = eventIDField.getText().trim();
        String sponsorID = sponsorIDField.getText().trim();

        // Check if fields are empty
        if (eventID.isEmpty() || sponsorID.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Both Event ID and Sponsor ID are required.");
            return;
        }

        // Validate Event ID format (numeric)
        if (!eventID.matches("\\d+")) { // Checks if eventID is numeric
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Event ID must be a numeric value.");
            return;
        }

        // Validate Sponsor ID format (numeric)
        if (!sponsorID.matches("\\d+")) { // Checks if sponsorID is numeric
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Sponsor ID must be a numeric value.");
            return;
        }

        // If all validations pass
        sponsorship=new Sponsorship();
        String name = sponsorship.retrieveName(sponsorID);
        if (sponsorship.removeSponsorship(Integer.parseInt(eventID), Integer.parseInt(sponsorID))) {
            // If removal was successful
            String message = "Sponsor Details:\nSponsor ID: "+sponsorID+". Sponsor Name: "+name+".\n";
            admin=UserFactory.createUser("ADMIN");
            eventOrganizer=UserFactory.createUser("EVENT_ORGANIZER");
            admin.addNotification(1, 1, message, "Sponsor has been removed!");
            eventOrganizer.addNotification(EventOrgID,2,message,"Sponsor has been removed!");
            showAlert(Alert.AlertType.INFORMATION, "Success", "Sponsor removed successfully!");
            ActionEvent ev=new ActionEvent();
            displayTableButtonOnAction(ev);
        } else {
            // If removal failed
            showAlert(Alert.AlertType.ERROR, "Failure", "Sponsor could not be removed as it does not exist.");
        }



    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional: Set to null to remove the header
        alert.setContentText(message);
        alert.showAndWait(); // Waits for the user to close the alert before continuing
    }


    public void displayTableButtonOnAction(ActionEvent actionEvent) {
        colEventID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        colEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colSponsorID.setCellValueFactory(new PropertyValueFactory<>("sponsorID"));
        colSponsorName.setCellValueFactory(new PropertyValueFactory<>("sponsorName"));
        colContributionAmount.setCellValueFactory(new PropertyValueFactory<>("contributionAmount"));

        // Load data from the database
        sponsorship=new Sponsorship();
        ObservableList<Sponsorship> sponsorshipList = FXCollections.observableArrayList();
        sponsorshipTable.setItems(sponsorship.intializeTable(sponsorshipList,EventOrgID));
    }

    public void setEventOrgID(int eventOrgID) {
        EventOrgID=eventOrgID;
    }
}