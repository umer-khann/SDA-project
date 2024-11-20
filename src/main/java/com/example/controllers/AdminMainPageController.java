package com.example.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainPageController implements Initializable {

    public JFXButton button3;
    public JFXButton button2;
    public JFXButton button1;
    public String a, b, c;

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

        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
                slider.setPrefWidth(176);
                contentArea.setPrefWidth(400);
                button1.setText(a);
                button2.setText(b);
                button3.setText(c);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
                slider.setPrefWidth(10);
                contentArea.setPrefWidth(730);
                button1.setText("");
                button2.setText("");
                button3.setText("");

            });
        });
    }

    // Method to load a new FXML into the content area
    private void loadPage(String fxml) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);

        slide.setToX(0);
        slide.play();

        slider.setTranslateX(0);
        button1.setText("");
        button2.setText("");
        button3.setText("");
        slide.setOnFinished((ActionEvent e)-> {
            Menu.setVisible(true);
            MenuClose.setVisible(false);
            slider.setPrefWidth(10);
        });
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
}
