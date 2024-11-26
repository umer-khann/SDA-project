package com.example.oopfiles;

public class PaymentNotification {
    private int paymentNotificationID;
    private Integer paymentID;
    private int userID;
    private String message;
    private String status;
    private String createdAt;
    public PaymentNotification(int paymentNotificationID, Integer paymentID, int userID,
                               String message, String status, String createdAt) {
        this.paymentNotificationID = paymentNotificationID;
        this.paymentID = paymentID;
        this.userID = userID;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }
    public PaymentNotification() {
    }
    // Getters and Setters
    public int getPaymentNotificationID() {
        return paymentNotificationID;
    }

    public void setPaymentNotificationID(int paymentNotificationID) {
        this.paymentNotificationID = paymentNotificationID;
    }

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
