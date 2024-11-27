package com.example.oopfiles;
public class TicketPurchaseNotification {
    private int ticketPurchaseNotificationID;
    private Integer ticketID;
    private int userID;
    private String message;
    private String status;
    private String createdAt;
    public TicketPurchaseNotification(int ticketPurchaseNotificationID, Integer ticketID, int userID,
                                      String message, String status, String createdAt) {
        this.ticketPurchaseNotificationID = ticketPurchaseNotificationID;
        this.ticketID = ticketID;
        this.userID = userID;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    public TicketPurchaseNotification() {
    }
    // Getters and Setters
    public int getTicketPurchaseNotificationID() {
        return ticketPurchaseNotificationID;
    }

    public void setTicketPurchaseNotificationID(int ticketPurchaseNotificationID) {
        this.ticketPurchaseNotificationID = ticketPurchaseNotificationID;
    }

    public Integer getTicketID() {
        return ticketID;
    }

    public void setTicketID(Integer ticketID) {
        this.ticketID = ticketID;
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
