package com.example.oopfiles;

public class Payment {
    private String paymentId;
    private double amount;
    private String status;
    private String transactionId;

    // Constructor
    public Payment(String paymentId, double amount, String status, String transactionId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
        this.transactionId = transactionId;
    }

    public Payment() {

    }

    // Getter and Setter methods
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // Methods
    public boolean processPayment(String ticketId, double amount) {
        // Logic for processing payment
        System.out.println("Processing payment for ticket ID: " + ticketId);
        return true; // Placeholder
    }

    public boolean confirmPayment(String transactionId) {
        // Logic for confirming payment
        System.out.println("Confirming payment with transaction ID: " + transactionId);
        return true; // Placeholder
    }
}
