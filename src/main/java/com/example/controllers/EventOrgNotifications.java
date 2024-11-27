package com.example.controllers;

import com.example.oopfiles.Notification;
import com.example.oopfiles.NotificationService;
import com.example.oopfiles.User;
import com.example.oopfiles.UserFactory;
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

public class EventOrgNotifications implements Initializable {



    private int EvOrgID;
    @FXML
    private TableView<Notification> Table;
    @FXML
    private TableColumn<Notification,Integer> userID;
    @FXML
    private TableColumn<Notification,String> message;
    @FXML
    private TableColumn<Notification,String> type;
    @FXML
    private TableColumn<Notification, Date> date;
    @FXML
    private TableColumn<Notification,Integer> evId;

    public void displayNotificationsButtonOnAction(ActionEvent actionEvent) {
        User admin = UserFactory.createUser("EVENT_ORGANIZER");
        NotificationService notificationService = new NotificationService();
        notificationService.addObserver(admin, "EVENT_ORGANIZER");
        ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();
        notificationObservableList.addAll(notificationService.notifyEventOrganizers(EvOrgID)); // Replace with your DB fetching method
        Table.setItems(notificationObservableList);
    }

    private void initializeNotificationTable() {
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        type.setCellValueFactory(new PropertyValueFactory<>("notificationType"));
        date.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        evId.setCellValueFactory(new PropertyValueFactory<>("eventID"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNotificationTable();
    }

    public void setEventOrgID(int eventOrgID) {
        this.EvOrgID=eventOrgID;
    }
}

