package com.example.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable {
    @FXML
    private ImageView Exit;
    @FXML
    private AnchorPane Confrence;
    @FXML
    private AnchorPane WorkShop;
    @FXML
    private AnchorPane Concert;
    @FXML
    private ChoiceBox<String> Type;
    private String[] ch = {"WorkShop", "Concert", "Confrence"};
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Type.getItems().addAll(ch);
        Type.setOnAction(this::getChoice);
        WorkShop.setVisible(true);
        Concert.setVisible(false);
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    private void getChoice(javafx.event.ActionEvent e) {
        String c = Type.getValue();
        if(Objects.equals(c, ch[0])){
            WorkShop.setVisible(true);
            Concert.setVisible(false);
            Confrence.setVisible(false);
        }
        else if(Objects.equals(c, ch[1])){
            WorkShop.setVisible(false);
            Concert.setVisible(true);
            Confrence.setVisible(false);
        }
        else if(Objects.equals(c, ch[2])){
            WorkShop.setVisible(false);
            Concert.setVisible(false);
            Confrence.setVisible(true);
        }
    }
}
