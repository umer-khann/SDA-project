package com.example.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainPageController implements Initializable {

    public JFXButton button3;

    public JFXButton button2;
    public JFXButton button1;
    public String a, b, c, d;

    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;

    @FXML
    private ScrollPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a = button1.getText();
        b = button2.getText();
        c = button3.getText();
        MenuClose.setVisible(false);
        Menu.setVisible(true);
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        slider.setTranslateX(-176);
        slider.setTranslateX(-176); // Initial position
        // Open menu transition
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(0); // Move slider to the right (open position)
            slide.play();

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
                slider.setPrefWidth(176); // Sidebar width after open
                contentArea.setPrefWidth(400); // Content width after open

                // Reset the buttons
                button1.setText(a);
                button2.setText(b);
                button3.setText(c);
            });
        });
    }

    // Method to load a new FXML into the content area
    private void loadPage(String fxml) {
        contentArea.setVisible(true);
        contentArea.setPrefWidth(730);
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("/com/example/sdaproj/" + fxml));

            // Clear the current content and set the new content
            contentArea.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace(); // Log the error if loading fails
        }
    }

    // Example event handlers for the buttons in the sidebar
    @FXML
    private void handleAddClick(ActionEvent event) {
        loadPage("attendee-reg.fxml"); // Load Add page
    }

    public void addEventOrgClick(ActionEvent actionEvent) {
        loadPage("event-org-reg.fxml");
    }



    public void HandleEvA(ActionEvent actionEvent) {
        loadPage("generate-event-analysis.fxml");
    }

    public void removeeventorg(ActionEvent actionEvent) {
        loadPage("Remove-Event-Organizer.fxml");
    }

    public void notif(ActionEvent actionEvent) {
        loadPage("Admin-Notifications.fxml");
    }
    public void LogOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/home-page.fxml"));
        BorderPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("");
        stage.show();
    }

    public void ex(ActionEvent actionEvent) {
        System.exit(0);
    }
}
