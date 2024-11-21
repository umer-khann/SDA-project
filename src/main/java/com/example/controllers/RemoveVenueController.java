package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveVenueController implements Initializable {
    @FXML
    private ImageView Exit;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Exit button functionality
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    public void buttonpress(ActionEvent actionEvent) {

    }
}
