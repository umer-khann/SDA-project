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

public class AddSponsershipController implements Initializable {
    @FXML
    private ImageView Exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }
}
