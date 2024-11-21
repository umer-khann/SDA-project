package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class HomePageController {

    public void attendeebutton(ActionEvent e) throws IOException {
        loadPage("attendee-login.fxml", e);
    }

    public void eventorgbutton(ActionEvent e) throws IOException {
        loadPage("event-organizer-login.fxml", e);
    }

    public void loginButtonOnAction(ActionEvent e) throws IOException {
        loadPage("hello-view.fxml", e);
    }

    private void loadPage(String fxmlFile, ActionEvent event) throws IOException {
        // Load the FXML file

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        BorderPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane

        // Get the stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("");
        // Set the new scene
        stage.setScene(new Scene(root));

        // Show the stage
        stage.show();
    }


}
