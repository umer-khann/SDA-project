package com.example.controllers;

import com.example.JDBC.AttendeeDBController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import com.example.oopfiles.Payment;
import com.example.oopfiles.Ticket;

import java.util.Random;

import static java.lang.Math.random;

public class HandleTicketAndPaymentController {

    @FXML
    private Label eventNameLabel;
    @FXML
    private Label eventVenueLabel;
    @FXML
    private Label eventDateLabel;
    @FXML
    private Label eventDetailsLabel;

    @FXML
    private TextField txtCreditCardNumber;
    @FXML
    private TextField txtCVVNumber;
    @FXML
    private TextField txtNameOnCard;
    @FXML
    private TextField txtMonth;
    @FXML
    private TextField txtYear;

    private String eventname;

    private int attendeeID;
    private int eventid;
    private int price = 0; // Default price

    // Method to set event details
    public void setEventDetails(String eventName, String eventVenue, String eventDate, String eventDetails) {
        eventname=eventName;
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

    // Method to set event ID
    public void setEventID(int eventId) {
        this.eventid = eventId;
    }

    public void saveDataAndPay(ActionEvent actionEvent) {
        // Get text field values
        String creditCardNumber = txtCreditCardNumber.getText();
        String cvv = txtCVVNumber.getText();
        String nameOnCard = txtNameOnCard.getText();
        String month = txtMonth.getText();
        String year = txtYear.getText();

        // Apply basic checks
        if (creditCardNumber.isEmpty() || cvv.isEmpty() || nameOnCard.isEmpty() || month.isEmpty() || year.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Validate credit card and CVV
        if (!Payment.validateCard(creditCardNumber)) {
            showAlert("Error", "Invalid credit card number.");
            return;
        }

        if (!Payment.validateCVV(cvv)) {
            showAlert("Error", "Invalid CVV.");
            return;
        }

        // Create a Random object
        Random random = new Random();
        // Generate a random number between 10 and 2000
        int paymentId =0;
        int ticketId= 0;
        int transactionId = random.nextInt(1865) + 5; // 1991 is the range (2000 - 10 + 1)

        Payment payment = new Payment(paymentId, price, "Pending", transactionId);

        // Create ticket object
        Ticket ticket = new Ticket(ticketId, price, "A1");
        ticket.setPayment(payment);


        int paymentid= payment.AddPaymentInformation(attendeeID,eventid);
        payment.setPaymentId(paymentId);
        System.out.println("Payment "+ paymentid);
        payment.AddNotification(attendeeID,paymentid);
        int ticket_id =ticket.AddTicketInformation(attendeeID,eventid,paymentid);
        ticket.setTicketId(ticket_id);
        System.out.println("ticket "+ ticket_id);
        ticket.insertTicketPurchaseNotification(attendeeID,ticket_id);
        if(!payment.AddEventAttendee(eventid,attendeeID))
        {
            showAlert("Error", "Already registered for this event");
            return;
        }


        payment.insertnotification(eventid, attendeeID,eventname);




        // Validate ticket
        if (!ticket.validateTicket(String.valueOf(attendeeID))) {
            showAlert("Error", "Ticket validation failed.");
            return;
        }




        // If everything is valid, confirm payment and show success message
        if (payment.confirmPayment(transactionId)) {
            showAlert("Success", "Payment successful. Ticket has been booked.");
        } else {
            showAlert("Error", "Payment confirmation failed.");
        }
    }


    public void showAlert(String type, String message) {
        Alert alert;
        if (type.equalsIgnoreCase("Success")) {
            alert = new Alert(Alert.AlertType.INFORMATION); // Information type for success
            alert.setHeaderText("Success");
        } else if (type.equalsIgnoreCase("Error")) {
            alert = new Alert(Alert.AlertType.ERROR); // Error type for failure
            alert.setHeaderText("Error");
        } else {
            alert = new Alert(Alert.AlertType.WARNING); // Default to warning
            alert.setHeaderText("Warning");
        }
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void setCreditCardType(KeyEvent keyEvent) {
    }
}
