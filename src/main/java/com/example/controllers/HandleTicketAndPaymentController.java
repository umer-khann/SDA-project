package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class HandleTicketAndPaymentController {

    @FXML
    private Label eventNameLabel;
    @FXML
    private Label eventVenueLabel;
    @FXML
    private Label eventDateLabel;
    @FXML
    private Label eventDetailsLabel;

    private int attendeeID;

    private int eventid;

    private int price = 0; // Default price


    // Method to set event details
    public void setEventDetails(String eventName, String eventVenue, String eventDate, String eventDetails) {
        eventNameLabel.setText(eventName);
        eventVenueLabel.setText(eventVenue);
        eventDateLabel.setText(eventDate);

        // Set price based on event type
        switch (eventDetails.toLowerCase()) {
            case "concert":
                price = 3000;
                break;
            case "conference":
                price = 2500;
                break;
            case "workshop":
                price = 4000;
                break;
            default:
                return;
        }

        // Set the price label or display
        eventDetailsLabel.setText("Price: " + price + " PKR");
    }


    // Method to set attendee ID
    public void setAttendeeID(int attendeeID) {
        this.attendeeID = attendeeID;
    }

    public void setEventID(int eventId) {
        this.eventid=eventId;
    }

    public void setCreditCardType(KeyEvent keyEvent) {
    }

    public void saveData(ActionEvent actionEvent) {
    }
}
