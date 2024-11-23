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

public class AttendeeMainPageController implements Initializable {

    public JFXButton button2;
    public JFXButton button1;
    private int attendeeID; // New field for AttendeeID
    public String a;
    public String b;

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
            });
        });
    }

    // Modified method to accept both EventorgID and AttendeeID
    private void loadPage(String fxml, int attendeeID) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);

        slide.setToX(0);
        slide.play();

        slider.setTranslateX(0);
        button1.setText("");
        button2.setText("");
        slide.setOnFinished((ActionEvent e)-> {
            Menu.setVisible(true);
            MenuClose.setVisible(false);
            slider.setPrefWidth(10);
        });
        contentArea.setVisible(true);
        contentArea.setPrefWidth(730);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxml));
            Parent pane = fxmlLoader.load();

            // Access the controller of the loaded FXML and set the AttendeeID
            Object controller = fxmlLoader.getController();
            if (controller instanceof AttendeeRegController) {
                ((AttendeeRegController) controller).setAttendeeID(attendeeID);
            } else if (controller instanceof AttendeeEventRegistrationController) {
                ((AttendeeEventRegistrationController) controller).setAttendeeID(attendeeID);
            }

            contentArea.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace(); // Log the error if loading fails
        }
    }

    // Example event handlers for the buttons in the sidebar
    @FXML
    private void handleDashboardClick(ActionEvent event) {
        System.out.println("AttendeeID set to: " + attendeeID);  // Debug line
        loadPage("attendee-event-registeration.fxml", attendeeID); // Pass AttendeeID
    }

    @FXML
    private void handleAddClick(ActionEvent event) {
        loadPage("attendee-reg.fxml", attendeeID); // Pass AttendeeID
    }

    @FXML
    private void HandleProvFeed(ActionEvent actionEvent) {
        loadPage("provide-feedback.fxml", attendeeID); // Pass AttendeeID
    }


    public void setAttendeeID(int attendeeID) {
        this.attendeeID=attendeeID;
    }
}
