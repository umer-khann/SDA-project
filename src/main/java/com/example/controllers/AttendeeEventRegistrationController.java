package com.example.controllers;

import com.example.JDBC.VenueDBHandler;
import com.example.oopfiles.*;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AttendeeEventRegistrationController {

    @FXML
    private Button applychanges;


    private int attendeeID;
    private String eventName;
    private String eventVenue;
    private String eventDate;
    private String eventDetails;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TextField eventid;

    @FXML
    private TableColumn<Event, Integer> colEventID;

    @FXML
    private TableColumn<Event, String> colEventName;

    @FXML
    private TableColumn<Event, String> colEventType;

    @FXML
    private TableColumn<Event, String> colEventDate;

    @FXML
    private TableColumn<Event, String> colVenueName;

    @FXML
    private TableColumn<Event, String> colPerformerName;
    @FXML
    private TableColumn<Event, String> colConferenceAgenda;
    @FXML
    private TableColumn<Event, String> colSpeakerName;
    @FXML
    private TableColumn<Event, String> colWorkshopTopic;
    @FXML
    private TableColumn<Event, String> colWorkshopInstructor;


    @FXML
    private TableColumn<Event, String> Details;


    private <T> void initializeColumn(TableColumn<Event, T> column, String property) {
        column.setCellValueFactory(new PropertyValueFactory<>(property));
    }



    public void setAttendeeID(int attendeeID) {
            this.attendeeID = attendeeID;
            System.out.println("AttendeeID set to: " + attendeeID);  // Debug line
    }

    // Initialize the table view and load event data based on the organizer's ID
    @FXML
    public void showEvents() {
        // Ensure currentOrganizerid is set
        if (attendeeID <= 0) {
            showAlert("Error", "No Attendee assigned. Please log in first.");
            return;
        }


        // Link columns to Event model properties
        initializeColumn(colEventID, "eventID");
        initializeColumn(colEventName, "eventName");
        initializeColumn(colEventType, "eventType");
        initializeColumn(colEventDate, "eventDate");
        initializeColumn(colVenueName, "venueName");


        colPerformerName.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ConcertEvent) {
                return new SimpleStringProperty(((ConcertEvent) cellData.getValue()).getPerformerName());
            }
            return new SimpleStringProperty(""); // Empty for other event types
        });

        // Add columns for specific event details
        colConferenceAgenda.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof ConferenceEvent) {
                return new SimpleStringProperty(((ConferenceEvent) cellData.getValue()).getEventAgenda());
            }
            return new SimpleStringProperty(""); // Empty for other event types
        });

        // Add columns for specific event details
        colWorkshopTopic.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof WorkshopEvent) {
                return new SimpleStringProperty(((WorkshopEvent) cellData.getValue()).getTopic());
            }
            return new SimpleStringProperty(""); // Empty for other event types
        });

//        initializeColumn(colWorkshopTopic, "workshopTopic");

        // Load and filter data from the database based on the organizer's ID
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        eventList = Event.initializeTableForAttendee(eventList);  // Filter events by organizer ID
        eventTable.setItems(eventList);


    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to set the AttendeeID and Event details
    public void setAttendeeDetails(int attendeeID, String eventName, String eventVenue, String eventDate, String eventDetails) {

    }

    @FXML
    public void buttonpress(ActionEvent e) throws IOException {
        // Get the event ID entered the TextField
        String enteredEventId = eventid.getText().trim();

        // Check if the input is valid
        if (enteredEventId.isEmpty()) {
            showAlert("Error", "Please enter an Event ID.");
            return;
        }

        int enteredId;
        try {
            enteredId = Integer.parseInt(enteredEventId); // Ensure it's a number
        } catch (NumberFormatException ex) {
            showAlert("Error", "Event ID must be a valid number.");
            return;
        }

        // Check if the entered Event ID exists in the TableView
        Event selectedEvent = eventTable.getItems().stream()
                .filter(event -> event.getEventID() == enteredId)
                .findFirst()
                .orElse(null);

        if (selectedEvent == null) {
            showAlert("Error", "No event found with the entered Event ID.");
            return;
        }

        // Extract event details from the found event
        eventName = selectedEvent.getEventName();
        eventVenue = selectedEvent.getVenueName();
        eventDate = selectedEvent.getEventDate();
        eventDetails = selectedEvent.getEventType();

        // Proceed to the next page with Event ID and Attendee ID
        AnchorPane parentPane = (AnchorPane) applychanges.getParent();

        if(!Event.AddEventAttendee(enteredId,attendeeID))
        {
            showAlert("Error","Attendee already registered for this event");
            return;
        }

        loadPage("Handle-ticket-and-payment.fxml", e, parentPane, enteredId, attendeeID);
    }



    // Method to load a new FXML into the content area
    private void loadPage(String fxml, ActionEvent e, AnchorPane contentArea, int eventId, int attendeeId) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setToX(0);
        slide.play();

        contentArea.setVisible(true);
        contentArea.setPrefWidth(730);
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxml));
            Parent pane = loader.load();

            // Pass event and attendee details to the next controller
            HandleTicketAndPaymentController nextController = loader.getController();
            nextController.setEventDetails(eventName, eventVenue, eventDate, eventDetails);
            nextController.setAttendeeID(attendeeId);
            nextController.setEventID(eventId); // Pass the selected Event ID

            // Clear the current content and set the new content
            contentArea.getChildren().clear(); // Clear any existing children
            contentArea.getChildren().add(pane); // Add the new FXML content as a child
        } catch (IOException e1) {
            e1.printStackTrace(); // Log the error if loading fails
        }
    }




    public TableColumn<Event, String> getColVenueName() {
        return colVenueName;
    }

    public void setColVenueName(TableColumn<Event, String> colVenueName) {
        this.colVenueName = colVenueName;
    }

    public TableColumn<Event, String> getColPerformerName() {
        return colPerformerName;
    }

    public void setColPerformerName(TableColumn<Event, String> colPerformerName) {
        this.colPerformerName = colPerformerName;
    }

    public TableColumn<Event, String> getColWorkshopTopic() {
        return colWorkshopTopic;
    }

    public void setColWorkshopTopic(TableColumn<Event, String> colWorkshopTopic) {
        this.colWorkshopTopic = colWorkshopTopic;
    }
}
