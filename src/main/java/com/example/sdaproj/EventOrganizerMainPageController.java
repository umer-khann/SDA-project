package com.example.sdaproj;

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

public class EventOrganizerMainPageController implements Initializable {

    public JFXButton button1;
    public JFXButton button2;
    public JFXButton button3;
    public JFXButton button4;
    public JFXButton button5;
    public JFXButton button6;
    public JFXButton button7;
    public JFXButton button8;
    public JFXButton button9;
    public JFXButton button10;
    public String a;
    public String b;
    public String c;
    public String d;
    public String e1;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;

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
        d = button4.getText();
        e1 = button5.getText();
        f = button6.getText();
        g = button7.getText();
        h = button8.getText();
        i = button9.getText();
        j = button10.getText();
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
                button4.setText(d);
                button5.setText(e1);
                button6.setText(f);
                button7.setText(g);
                button8.setText(h);
                button9.setText(i);
                button10.setText(j);
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
                button4.setText("");
                button5.setText("");
                button6.setText("");
                button7.setText("");
                button8.setText("");
                button9.setText("");
                button10.setText("");
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
        button4.setText("");
        button5.setText("");
        button6.setText("");
        button7.setText("");
        button8.setText("");
        button9.setText("");
        button10.setText("");
        slide.setOnFinished((ActionEvent e)-> {
            Menu.setVisible(true);
            MenuClose.setVisible(false);
            slider.setPrefWidth(10);
        });
        contentArea.setVisible(true);
        contentArea.setPrefWidth(730);
        try {
            // Load the FXML file
            Parent pane = FXMLLoader.load(getClass().getResource(fxml));
            // Clear the current content and set the new content
            contentArea.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace(); // Log the error if loading fails
        }
    }

    // Example event handlers for the buttons in the sidebar
    @FXML
    private void HandleCrEv(ActionEvent event) {
        loadPage("Create-event.fxml"); // Load Dashboard page
    }

    @FXML
    private void handleAddClick(ActionEvent event) {
        loadPage("attendee-reg.fxml"); // Load Add page
    }

    public void ManageEventClick(ActionEvent actionEvent) {
        loadPage("manage-event.fxml");
    }

    public void ManageAttendeeClick(ActionEvent actionEvent) {
        loadPage("select-event.fxml");
    }

    public void RemoveSponsorshipClick(ActionEvent actionEvent) {
        loadPage("remove-sponsorship.fxml");
    }

    public void budgetClick(ActionEvent actionEvent) {
        loadPage("handle-event-budget.fxml");
    }

    public void AddVenueClick(ActionEvent actionEvent) {
        loadPage("add-new-venue.fxml");
    }

    public void HandleRemVen(ActionEvent actionEvent) { loadPage("remove-venue.fxml");}

    public void HandleAddSp(ActionEvent actionEvent) {
        loadPage("add-sponsership.fxml");
    }

    public void eventresources(ActionEvent actionEvent) {
        loadPage("Allocate-And-Track-Event-Resources.fxml");
    }
}

