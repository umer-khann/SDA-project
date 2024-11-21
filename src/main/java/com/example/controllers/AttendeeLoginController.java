package com.example.controllers;

import com.example.oopfiles.Attendee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AttendeeLoginController {

    @FXML
    private Label welcomeText;
    @FXML
    public Label loginmessagelabel;

    @FXML
    public Button loginbutton;

    @FXML
    private TextField uname;

    @FXML
    private PasswordField upass;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void loginbuttonOnAction(ActionEvent e) throws IOException {
        String username = uname.getText();
        String password = upass.getText();

        if (!username.isBlank() && !password.isBlank()) {
            Attendee attendee = new Attendee(username, password);

            // Validate login
            if (attendee.validateLogin()) {
                // If login is successful, load the main attendee page
                loadPage2("Attendee-main-page.fxml", e);
            } else {
                // Display an error message
                loginmessagelabel.setText("Invalid username or password. Please try again.");
            }
        } else {
            loginmessagelabel.setText("Please input full details.");
        }
    }

    public void backButtonOnAction(ActionEvent e) throws IOException {
        loadPage("home-page.fxml", e);
    }

    public void signUpButtonOnAction(ActionEvent e) throws IOException {
        loadPage("attendee-reg.fxml", e);
    }

    private void loadPage(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        BorderPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("");
        stage.show();
    }

    private void loadPage2(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        AnchorPane root = fxmlLoader.load(); // Assuming the root node is an AnchorPane

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("");
        stage.show();
    }

}
