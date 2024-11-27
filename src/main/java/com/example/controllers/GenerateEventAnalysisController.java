package com.example.controllers;

import com.example.oopfiles.Event;
import com.example.oopfiles.EventAnalysis;
import com.example.oopfiles.Venue;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GenerateEventAnalysisController implements Initializable {
    @FXML
    private ImageView Exit;

    @FXML
    private TableColumn<EventAnalysis, Integer> EvID;
    @FXML
    private TableColumn<EventAnalysis, String> EvName;
    @FXML
    private TableColumn<EventAnalysis, Date> EvDate;
    @FXML
    private TableColumn<EventAnalysis, String> EvVenue;
    @FXML
    private TableColumn<EventAnalysis, Integer> EvTickets;
    @FXML
    private TableColumn<EventAnalysis, Double> EvRating;
    @FXML
    private TableView<EventAnalysis> Table;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialEventAnalysis();
    }
    private void initialEventAnalysis() {
        EvID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        EvName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        EvDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        EvVenue.setCellValueFactory(new PropertyValueFactory<>("venueName"));
        EvTickets.setCellValueFactory(new PropertyValueFactory<>("ticketsSold"));
        EvRating.setCellValueFactory(new PropertyValueFactory<>("avgRating"));
    }

    // Load venue data into the table
    private void loadEventAnalysis() {
        EventAnalysis eventAnalysis = new EventAnalysis();
        ObservableList<EventAnalysis> eventAnalyses = FXCollections.observableArrayList();
        List<EventAnalysis> analysis = eventAnalysis.generateeventanalysis(eventAnalyses);  // Check if this list is not empty
        eventAnalyses.addAll(analysis);
        Table.setItems(eventAnalyses);
    }
    public void Generate(ActionEvent actionEvent) {
        loadEventAnalysis();
    }
}
