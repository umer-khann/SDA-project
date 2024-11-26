package com.example.oopfiles;

import com.example.JDBC.PaymentDBHandler;

public class Payment {
    private int paymentId;
    private double amount;
    private String status;
    private int transactionId;
    PaymentDBHandler db;

    // Constructor
    public Payment(int paymentId, double amount, String status, int transactionId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
        this.transactionId = transactionId;
        db= new PaymentDBHandler();
    }

    public Payment() {}

    // Getter and Setter methods
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    // Methods
    public boolean processPayment(int ticketId) {
        // Logic for processing payment
        System.out.println("Processing payment for ticket ID: " + ticketId);
        return true; // Placeholder, this can be replaced with real payment processing logic
    }

    public boolean confirmPayment(int transactionId) {
        // Logic for confirming payment
        System.out.println("Confirming payment with transaction ID: " + transactionId);
        return true; // Placeholder
    }

    public static boolean validateCard(String cardNumber) {
        // A simple validation based on length, extend with Luhn algorithm or other logic
        return cardNumber.length() == 16;
    }

    public static boolean validateCVV(String cvv) {
        return cvv.length() == 3 || cvv.length() == 4;
    }

    public void AddNotification(int attendeeID,int paymentIdd)
    {
            this.setPaymentId(paymentIdd);
            PaymentDBHandler.insertPaymentNotification(paymentIdd,attendeeID,"Payment Approved", amount);
    }



    public void insertnotification(int eventId, int userId, String eventname) {
        PaymentDBHandler.insertEventRegistrationNotification(eventId, userId, "Registration Successful for event: "+ eventname, "Unread");
    }

    public int AddPaymentInformation(int attendeeID, int eventid)
    {
        return PaymentDBHandler.insertPayment(amount,"Success",transactionId,attendeeID,eventid);
    }
}


