package com.example.sdaproj;

import com.example.oopfiles.Tracker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class HomePage extends Application {
    @Override

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomePage.class.getResource("home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
