package com.example.controllers;

import com.example.oopfiles.Attendee;
import com.example.oopfiles.GeneralAttendee;
import com.example.oopfiles.VipAttendee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AttendeeLoginController {

    private Attendee attendee;
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
            Attendee temp = new VipAttendee();
            Attendee temp1 = new GeneralAttendee();
            if (temp.login(username, password)) {
                attendee = new VipAttendee();
                attendee.Create(username, password);
                // Assuming attendee has an ID that you want to pass
                int attendeeID = attendee.getId();
                loadPage2("Attendee-main-page.fxml", e, attendeeID);

            } else if (temp1.login(username, password)) {
                attendee = new GeneralAttendee();
                attendee.Create(username, password);
                int attendeeID = attendee.getId();
                loadPage2("Attendee-main-page.fxml", e, attendeeID);

            } else {
                // Display an error message
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid username or password. Please try again.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please input full details.");

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

    private void loadPage2(String fxmlFile, ActionEvent event, int attendeeID) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        AnchorPane root = fxmlLoader.load(); // Assuming the root node is an AnchorPane

        // Get the next controller and pass the attendeeID
        AttendeeMainPageController nextController = fxmlLoader.getController();
        nextController.setAttendeeID(attendeeID); // Set attendeeID in the next controller

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("");
        stage.show();

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional: Set to null to remove the header
        alert.setContentText(message);
        alert.showAndWait(); // Waits for the user to close the alert before continuing
    }


    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
