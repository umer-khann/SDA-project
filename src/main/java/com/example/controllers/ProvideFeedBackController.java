package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProvideFeedBackController implements Initializable {
    @FXML
    private ImageView Exit;
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;
    @FXML
    private ImageView star3;
    @FXML
    private ImageView star4;
    @FXML
    private ImageView star5;
    public Image on, off;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL onImageUrl = getClass().getResource("/com/example/sdaproj/images/star.png");
        URL offImageUrl = getClass().getResource("/com/example/sdaproj/images/star-off.png");

        // Check if the resources were found
        if (onImageUrl != null && offImageUrl != null) {
            on = new Image(onImageUrl.toExternalForm());
            off = new Image(offImageUrl.toExternalForm());
        } else {
            // Log or handle the case where the resources are missing
            System.out.println("Image resources not found. Check your paths.");
            return;  // Prevent further execution if images are not found
        }

        // Exit button functionality
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        // Star rating functionality
        star1.setOnMouseClicked(event ->{
            star1.setImage(on);
            star2.setImage(off);
            star3.setImage(off);
            star4.setImage(off);
            star5.setImage(off);
        });
        star2.setOnMouseClicked(event ->{
            star1.setImage(on);
            star2.setImage(on);
            star3.setImage(off);
            star4.setImage(off);
            star5.setImage(off);
        });
        star3.setOnMouseClicked(event ->{
            star1.setImage(on);
            star2.setImage(on);
            star3.setImage(on);
            star4.setImage(off);
            star5.setImage(off);
        });
        star4.setOnMouseClicked(event ->{
            star1.setImage(on);
            star2.setImage(on);
            star3.setImage(on);
            star4.setImage(on);
            star5.setImage(off);
        });
        star5.setOnMouseClicked(event ->{
            star1.setImage(on);
            star2.setImage(on);
            star3.setImage(on);
            star4.setImage(on);
            star5.setImage(on);
        });
    }
}
