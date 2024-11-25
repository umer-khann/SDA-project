package com.example.controllers;

import com.example.JDBC.VenueDBHandler;
import com.example.oopfiles.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminNotifications implements Initializable {
    @FXML
    private TableView<Notification> Table;
    @FXML
    private TableColumn<Notification,Integer> userID;
    @FXML
    private TableColumn<Notification,Integer> userType;
    @FXML
    private TableColumn<Notification,String> message;
    @FXML
    private TableColumn<Notification,String> type;
    @FXML
    private TableColumn<Notification, Date> date;
    @FXML
    private TableColumn<Notification,Integer> evId;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the Venue Table columns
        initializeNotificationTable();
    }

    // Initialize Venue Table columns
    private void initializeNotificationTable() {
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        userType.setCellValueFactory(new PropertyValueFactory<>("userType"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        type.setCellValueFactory(new PropertyValueFactory<>("notificationType"));
        date.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        evId.setCellValueFactory(new PropertyValueFactory<>("eventID"));
    }
    public void displaypushed(ActionEvent e){
        loadData();
    }
    // Load venue data into the table
    private void loadData() {
        User admin = new Admin();
        NotificationService notificationService = new NotificationService();
        notificationService.addObserver(admin, "ADMIN");
        ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();
        notificationObservableList.addAll(notificationService.notifyAdmins()); // Replace with your DB fetching method
        Table.setItems(notificationObservableList);
    }
}
