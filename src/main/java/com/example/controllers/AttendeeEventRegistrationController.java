package com.example.controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AttendeeEventRegistrationController{

    @FXML
    private Button applychanges;


    public void buttonpress(ActionEvent e) throws IOException {
        AnchorPane parentPane = (AnchorPane) applychanges.getParent();
        Stage stage = (Stage) applychanges.getScene().getWindow();
        loadPage("Handle-ticket-and-payment.fxml", e, parentPane);
    }

    // Method to load a new FXML into the content area
    private void loadPage(String fxml, ActionEvent e, AnchorPane contentArea) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setToX(0);
        slide.play();

        contentArea.setVisible(true);
        contentArea.setPrefWidth(730);
        try {
            // Load the FXML file
            Parent pane = FXMLLoader.load(getClass().getResource("/com/example/sdaproj/" + fxml));

            // Clear the current content and set the new content
            contentArea.getChildren().clear(); // Clear any existing children
            contentArea.getChildren().add(pane); // Add the new FXML content as a child
        } catch (IOException e1) {
            e1.printStackTrace(); // Log the error if loading fails
        }
    }
}

