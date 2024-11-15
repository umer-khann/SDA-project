package com.example.sdaproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AttendeeLoginController {

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


        @FXML
        protected void onHelloButtonClick() {
            welcomeText.setText("Welcome to JavaFX Application!");
        }

        @FXML
        public void loginbuttonOnAction(javafx.event.ActionEvent e) {
            loginmessagelabel.setText("You tried to login");

            if(!uname.getText().isBlank() && !upass.getText().isBlank()){
                loginmessagelabel.setText("You tried to login");
            }
            else {
                loginmessagelabel.setText("Please input full details");
            }
        }
    public void backButtonOnAction(javafx.event.ActionEvent e) throws IOException {
        loadPage("home-page.fxml", e);
    }
    private void loadPage(String fxmlFile, ActionEvent event) throws IOException {
        // Load the FXML file

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        BorderPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane

        // Get the stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(root));

        // Show the stage
        stage.show();
    }
    }
