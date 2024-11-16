package com.example.sdaproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewVenueController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField capacityField;
    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private TextField weatherField;
    @FXML
    private TextField additionalCapacityField;

    @FXML
    private TextField roomField;
    @FXML
    private TextField floorField;

    @FXML
    private Label weatherLabel;
    @FXML
    private Label additionalCapacityLabel;
    @FXML
    private Label roomLabel;
    @FXML
    private Label floorLabel;

    private String[] choices = {"Indoor", "Outdoor"};
    private String choice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myChoiceBox.getItems().addAll(choices);
        myChoiceBox.setOnAction(this::getChoice);
    }

    public void getChoice(ActionEvent event) {
        choice = myChoiceBox.getValue();

        // Show or hide fields based on the selected venue type
        if ("Outdoor".equals(choice)) {
            showOutdoorFields();
        } else if ("Indoor".equals(choice)) {
            showIndoorFields();
        }
    }

    private void showOutdoorFields() {
        // Show fields for outdoor venue
        weatherLabel.setVisible(true);
        weatherField.setVisible(true);
        additionalCapacityLabel.setVisible(true);
        additionalCapacityField.setVisible(true);

        // Hide indoor venue fields
        roomLabel.setVisible(false);
        roomField.setVisible(false);
        floorLabel.setVisible(false);
        floorField.setVisible(false);
    }

    private void showIndoorFields() {
        // Show fields for indoor venue
        roomLabel.setVisible(true);
        roomField.setVisible(true);
        floorLabel.setVisible(true);
        floorField.setVisible(true);

        // Hide outdoor venue fields
        weatherLabel.setVisible(false);
        weatherField.setVisible(false);
        additionalCapacityLabel.setVisible(false);
        additionalCapacityField.setVisible(false);
    }

    public void AddVenueOnAction(ActionEvent actionEvent) {
        // Handle adding venue logic here
    }
}
