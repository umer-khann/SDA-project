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
    private Label loginmessagelabel;
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
                loginmessagelabel.setText("Invalid credentials! Try again.");
            }
        } else {
            loginmessagelabel.setText("Please input full details.");
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

}
