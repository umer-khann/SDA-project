package com.example.controllers;

import com.example.oopfiles.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class EventOrganizerController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label loginmessagelabel;


    @FXML
    private Button loginbutton;

    @FXML
    private TextField uname;

    @FXML
    private PasswordField upass;
    Tracker t;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    public EventOrganizerController(){
        t = new Tracker();

    }
    @FXML
    public void loginbuttonOnAction(javafx.event.ActionEvent e) throws IOException {
        // Load the new FXML file
        User evOrg = null;
        String username = uname.getText(), password = upass.getText();
        if (!username.isBlank() && !password.isBlank()) {
            User temp = new EventOrganizer();
            if(temp.login(username,password)){
                evOrg = new EventOrganizer();
                evOrg.Create(username,password);
                Tracker t = new Tracker();
                System.out.println(evOrg);
                System.out.println(t.eventOrganizerID);
                loadPage2("eventorganizer-main-page.fxml", e,evOrg.getUserID()
                );

            }
            else{
                // Display an error message
                loginmessagelabel.setText("Invalid username or password. Please try again.");
            }
        } else {
            loginmessagelabel.setText("Please input full details.");
        }
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
    private void loadPage2(String fxmlFile, ActionEvent event, int ID) throws IOException {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        AnchorPane root = fxmlLoader.load();

        // Get the controller of the new FXML
        EventOrganizerMainPageController controller = fxmlLoader.getController();
        // Pass the event organizer ID to the new controller
        controller.setEventorgID(ID);

        // Get the stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("");

        // Set the new scene
        stage.setScene(new Scene(root));

        // Show the stage
        stage.show();
    }

}