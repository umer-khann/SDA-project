package com.example.sdaproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNewVenueController implements Initializable {
    @FXML
    private Label signUpmessagelabel;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameField;



    @FXML
    private ChoiceBox<String> myChoiceBox;

    private String[] choices = {"Indoor","Outdoor"};

    private String choice;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        myChoiceBox.getItems().addAll(choices);
        myChoiceBox.setOnAction(this::getChoice);
    }
    public void getChoice(javafx.event.ActionEvent e){
        choice=myChoiceBox.getValue();
    }

    public void AddVenueOnAction(ActionEvent actionEvent) {

    }
}
