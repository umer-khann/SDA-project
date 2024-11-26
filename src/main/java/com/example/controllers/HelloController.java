package com.example.controllers;

import com.example.oopfiles.Admin;
import com.example.oopfiles.User;
import com.example.oopfiles.UserFactory;
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

public class HelloController {

    @FXML
    private TextField uname;
    @FXML
    private PasswordField upass;

    User admin = UserFactory.createUser("ADMIN");// Singleton instance of Admin

    @FXML
    public void loginbuttonOnAction(ActionEvent e) throws IOException {
        String username = uname.getText();
        String password = upass.getText();

        if (!username.isBlank() && !password.isBlank()) {
            if (admin.login(username, password)) {
                // Navigate to the Admin main page
                navigateTo("admin-main-page.fxml", e);
            } else {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid Credentials! Try again.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please input full details.");
        }
    }

    @FXML
    public void backButtonOnAction(ActionEvent e) throws IOException {
        // Navigate to the Home page
        navigateTo1("home-page.fxml", e);
    }

    private void navigateTo(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        AnchorPane root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    private void navigateTo1(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxmlFile));
        BorderPane root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional: Set to null to remove the header
        alert.setContentText(message);
        alert.showAndWait(); // Waits for the user to close the alert before continuing
    }

}
