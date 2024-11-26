package com.example.controllers;

import com.example.oopfiles.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.oopfiles.UserFactory.*;

public class AttendeeRegController implements Initializable {
    @FXML
    private Label signUpmessagelabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ChoiceBox<String> myChoiceBox;

    private String[] choices = {"General","VIP"};

    private String choice;

    @FXML
    private TextField vipLevelField;

    @FXML
    private Label vipLevelLabel;

    @FXML
    private TextField membershipLevelField;

    @FXML
    private Label membershipLevelLabel;



    @FXML
    public void signUpButtonOnAction(javafx.event.ActionEvent e) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String contact = contactField.getText();
        String email = emailField.getText();
        String additionalDetail = null;

        // Check for empty fields
        if (username.isBlank() || password.isBlank() || contact.isBlank() || email.isBlank()) {
            showAlert("Input Error", "Please input full details!");
            return;
        }

        // Check for additional fields based on choice
        if ("VIP".equalsIgnoreCase(choice)) {
            additionalDetail = vipLevelField.getText();
            if (additionalDetail.isBlank()) {
                showAlert("Input Error", "Please input VIP level!");
                return;
            }
        } else if ("General".equalsIgnoreCase(choice)) {
            additionalDetail = membershipLevelField.getText();
            if (additionalDetail.isBlank()) {
                showAlert("Input Error", "Please input Membership level!");
                return;
            }
        }

        // Rest of your sign-up logic
        User newAttendee = null;
        if ("General".equalsIgnoreCase(choice)) {
            newAttendee = createUser("GENERAL_ATTENDEE");
            newAttendee.setType("General");
        } else if ("VIP".equalsIgnoreCase(choice)) {
            newAttendee = createUser("VIP_ATTENDEE");
            newAttendee.setType("VIP");
        }
        newAttendee.setName(nameField.getText());
        newAttendee.setEmail(emailField.getText());
        newAttendee.setContactDetails(contactField.getText());
        newAttendee.setUserName(username);
        newAttendee.setPassword(password);
        // Validate and register the attendee
        if (!newAttendee.isValidUserName() || !newAttendee.isValidEmail()) {
            showAlert("Registration Error", "Invalid details provided!");
            return;
        }

        RegisterAdapter userAdapter = new UserRegisterAdapter(newAttendee);
        if(userAdapter.register(additionalDetail))// Load the next page
            loadPage("home-page.fxml", e);
    }


    // Method to display alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);  // You can set a header text if needed
        alert.setContentText(message);
        alert.showAndWait();
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
    private void loadPage2(String fxmlFile, ActionEvent event) throws IOException {
        // Load the FXML file

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        AnchorPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane

        // Get the stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene
        stage.setScene(new Scene(root));
        stage.setTitle("");
        // Show the stage
        stage.show();
    }


    public void getChoice(javafx.event.ActionEvent e) {
        choice = myChoiceBox.getValue();

        // Show or hide additional fields based on choice
        if ("VIP".equalsIgnoreCase(choice)) {
            vipLevelField.setVisible(true);
            vipLevelLabel.setVisible(true);
            membershipLevelField.setVisible(false);
            membershipLevelLabel.setVisible(false);
        } else if ("General".equalsIgnoreCase(choice)) {
            vipLevelField.setVisible(false);
            vipLevelLabel.setVisible(false);
            membershipLevelField.setVisible(true);
            membershipLevelLabel.setVisible(true);
        } else {
            // Hide all additional fields if no valid choice is selected
            vipLevelField.setVisible(false);
            vipLevelLabel.setVisible(false);
            membershipLevelField.setVisible(false);
            membershipLevelLabel.setVisible(false);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        myChoiceBox.getItems().addAll(choices);
        myChoiceBox.setOnAction(this::getChoice);

        // Initially hide additional fields
        vipLevelField.setVisible(false);
        vipLevelLabel.setVisible(false);
        membershipLevelField.setVisible(false);
        membershipLevelLabel.setVisible(false);
    }


    public void backButtonOnAction(javafx.event.ActionEvent e) throws IOException {
        loadPage("home-page.fxml", e);
    }

    public void setAttendeeID(int attendeeID) {
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
