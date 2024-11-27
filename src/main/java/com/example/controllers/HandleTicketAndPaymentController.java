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
        String creditCardNumber = txtCreditCardNumber.getText().trim();
        String cvv = txtCVVNumber.getText().trim();
        String nameOnCard = txtNameOnCard.getText().trim();
        String month = txtMonth.getText().trim();
        String year = txtYear.getText().trim();

        // Apply basic checks
        if (creditCardNumber.isEmpty() || cvv.isEmpty() || nameOnCard.isEmpty() || month.isEmpty() || year.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Length validation
        if (creditCardNumber.length() < 13 || creditCardNumber.length() > 19) {
            showAlert("Error", "Credit card number must be between 13 and 19 digits.");
            return;
        }

        if (cvv.length() != 3 && cvv.length() != 4) {
            showAlert("Error", "CVV must be 3 or 4 digits.");
            return;
        }

        // Numeric validation
        if (!creditCardNumber.matches("\\d+")) {
            showAlert("Error", "Credit card number must contain only digits.");
            return;
        }

        if (!cvv.matches("\\d+")) {
            showAlert("Error", "CVV must contain only digits.");
            return;
        }

        if (!month.matches("\\d+") || !year.matches("\\d+")) {
            showAlert("Error", "Month and year must contain only digits.");
            return;
        }

        // Month validation
        int monthInt = Integer.parseInt(month);
        if (monthInt < 1 || monthInt > 12) {
            showAlert("Error", "Month must be between 1 and 12.");
            return;
        }

        // Year validation
        int currentYear = java.time.Year.now().getValue();
        int yearInt = Integer.parseInt(year);
        if (yearInt < currentYear || yearInt > currentYear + 20) {
            showAlert("Error", "Year must be valid and within a realistic range.");
            return;
        }

        // Expiry date validation
        if (yearInt == currentYear && monthInt < java.time.LocalDate.now().getMonthValue()) {
            showAlert("Error", "Card expiration date must be in the future.");
            return;
        }

        // Name validation
        if (!nameOnCard.matches("[a-zA-Z ]+")) {
            showAlert("Error", "Name on card must contain only alphabetic characters and spaces.");
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

        //Payment payment = new Payment(paymentId, price, "Pending", transactionId);

        // Create ticket object
        Ticket ticket = new Ticket(ticketId, price, "A1");
        ticket.setPayment(paymentId, price,"Pending",transactionId);


        int paymentid= ticket.GetPayment().AddPaymentInformation(attendeeID,eventid);
        ticket.GetPayment().setPaymentId(paymentId);//AddPaymentInformation(attendeeID,eventid);
        System.out.println("Payment "+ paymentid);
        ticket.GetPayment().AddNotification(attendeeID,paymentid);
        int ticket_id =ticket.AddTicketInformation(attendeeID,eventid,paymentid);
        ticket.setTicketId(ticket_id);
        //
        System.out.println("ticket "+ ticket_id);
        ticket.insertTicketPurchaseNotification(attendeeID,ticket_id, eventname);


        ticket.GetPayment().insertnotification(eventid, attendeeID,eventname);




        // Validate ticket
        if (!ticket.validateTicket(String.valueOf(attendeeID))) {
            showAlert("Error", "Ticket validation failed.");
            return;
        }




        // If everything is valid, confirm payment and show success message
        if (ticket.GetPayment().confirmPayment(transactionId)) {
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
