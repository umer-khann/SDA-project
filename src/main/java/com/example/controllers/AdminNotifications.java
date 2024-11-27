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
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminNotifications implements Initializable {
    @FXML
    private AnchorPane GeneralPane;
    @FXML
    private AnchorPane TicketPane;
    @FXML

    private TableView<TicketPurchaseNotification> Table1;
    @FXML
    private TableColumn<TicketPurchaseNotification,Integer> TicketID;
    @FXML
    private TableColumn<TicketPurchaseNotification,Integer> TickUserID;
    @FXML
    private TableColumn<TicketPurchaseNotification,String> Tickmessage;
    @FXML
    private TableColumn<TicketPurchaseNotification,String> Tickdate;
    @FXML
    private AnchorPane PaymentPane;
    @FXML
    private TableView<PaymentNotification> Table2;
    @FXML
    private TableColumn<PaymentNotification,Integer> PayID;
    @FXML
    private TableColumn<PaymentNotification,Integer> PayUserID;
    @FXML
    private TableColumn<PaymentNotification,String> Paymessage;
    @FXML
    private TableColumn<PaymentNotification,String> Paydate;
    @FXML
    private AnchorPane EventRegPane;
    @FXML
    private TableView<EventRegistrationNotification> Table21;
    @FXML
    private TableColumn<EventRegistrationNotification,Integer> EVREGEvID;
    @FXML
    private TableColumn<EventRegistrationNotification,Integer> EVREGUserID;
    @FXML
    private TableColumn<EventRegistrationNotification,String> EVREGmessage;
    @FXML
    private TableColumn<EventRegistrationNotification,String> EVREGtype;
    @FXML
    private TableColumn<EventRegistrationNotification,String> EVREGdate;
    @FXML
    private AnchorPane EventUpdatePane;
    @FXML
    private TableView<EventUpdateNotification> Table22;
    @FXML
    private TableColumn<EventUpdateNotification,Integer> EVUPEventID;
    @FXML
    private TableColumn<EventUpdateNotification,Integer> EVUPUserID;
    @FXML
    private TableColumn<EventUpdateNotification,String> EVUPmessage;
    @FXML
    private TableColumn<EventUpdateNotification,String> EVUPdate;
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
        initializeEventRegTable();
        initializeEventUpTable();
        initializePaymentTable();
        initializeTicketTable();
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
    private void initializePaymentTable() {
        PayID.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        PayUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        Paymessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        Paydate.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    }
    private void initializeEventRegTable() {
        EVREGEvID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        EVREGUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        EVREGmessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        EVREGtype.setCellValueFactory(new PropertyValueFactory<>("status"));
        EVREGdate.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    } private void initializeEventUpTable() {
        EVUPEventID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        EVUPUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        EVUPmessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        EVUPdate.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    } private void initializeTicketTable() {
        TicketID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        TickUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        Tickmessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        Tickdate.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    }
    public void displaypushed(ActionEvent e){
        GeneralPane.setVisible(true);
        TicketPane.setVisible(false);
        PaymentPane.setVisible(false);
        EventRegPane.setVisible(false);
        EventUpdatePane.setVisible(false);
        loadData();
    }
    // Load venue data into the table
    private void loadData() {
        User admin = UserFactory.createUser("ADMIN");
        NotificationService notificationService = new NotificationService();
        notificationService.addObserver(admin, "ADMIN");
        ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();
        notificationObservableList.addAll(notificationService.notifyAdmins()); // Replace with your DB fetching method
        Table.setItems(notificationObservableList);
    }
    private void loadData1() {
        User admin = UserFactory.createUser("ADMIN");
        NotificationService notificationService = new NotificationService();
        notificationService.addObserver(admin, "ADMIN");
        ObservableList<EventRegistrationNotification> notificationObservableList = FXCollections.observableArrayList();
        notificationObservableList.addAll(notificationService.notifyAdminsEvUp()); // Replace with your DB fetching method
        Table21.setItems(notificationObservableList);
    }private void loadData2() {
        User admin = UserFactory.createUser("ADMIN");
        NotificationService notificationService = new NotificationService();
        notificationService.addObserver(admin, "ADMIN");
        ObservableList<EventUpdateNotification> notificationObservableList = FXCollections.observableArrayList();
        notificationObservableList.addAll(notificationService.notifyAdminsEvCr()); // Replace with your DB fetching method
        Table22.setItems(notificationObservableList);
    }
    private void loadData3() {
        User admin = UserFactory.createUser("ADMIN");
        NotificationService notificationService = new NotificationService();
        notificationService.addObserver(admin, "ADMIN");
        ObservableList<TicketPurchaseNotification> notificationObservableList = FXCollections.observableArrayList();
        notificationObservableList.addAll(notificationService.notifyAdminsTiPu());
        Table1.setItems(notificationObservableList);
    }

    private void loadData4() {
        User admin = UserFactory.createUser("ADMIN");
        NotificationService notificationService = new NotificationService();
        notificationService.addObserver(admin, "ADMIN");
        ObservableList<PaymentNotification> notificationObservableList = FXCollections.observableArrayList();
        notificationObservableList.addAll(notificationService.notifyAdminsPaN());
        Table2.setItems(notificationObservableList);
    }

    public void paymentPushed(ActionEvent actionEvent) {
        GeneralPane.setVisible(false);
        TicketPane.setVisible(false);
        PaymentPane.setVisible(true);
        EventRegPane.setVisible(false);
        EventUpdatePane.setVisible(false);
        loadData4();
    }

    public void TickPushed(ActionEvent actionEvent) {
        GeneralPane.setVisible(false);
        TicketPane.setVisible(true);
        PaymentPane.setVisible(false);
        EventRegPane.setVisible(false);
        EventUpdatePane.setVisible(false);
        loadData3();
    }

    public void EvUpPushed(ActionEvent actionEvent) {
        GeneralPane.setVisible(false);
        TicketPane.setVisible(false);
        PaymentPane.setVisible(false);
        EventRegPane.setVisible(false);
        EventUpdatePane.setVisible(true);
        loadData2();
    }

    public void EvRedPushed(ActionEvent actionEvent) {
        GeneralPane.setVisible(false);
        TicketPane.setVisible(false);
        PaymentPane.setVisible(false);
        EventRegPane.setVisible(true);
        EventUpdatePane.setVisible(false);
        loadData1();
    }
}
