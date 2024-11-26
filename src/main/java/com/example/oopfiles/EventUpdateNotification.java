package com.example.oopfiles;

public class EventUpdateNotification {
    private int eventUpdateNotificationID;
    private Integer eventID;
    private int userID;
    private String message;
    private String status;
    private String createdAt;
    public EventUpdateNotification(int eventUpdateNotificationID, Integer eventID, int userID,
                                   String message, String status, String createdAt) {
        this.eventUpdateNotificationID = eventUpdateNotificationID;
        this.eventID = eventID;
        this.userID = userID;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }
    public EventUpdateNotification() {
    }

    // Getters and Setters
    public int getEventUpdateNotificationID() {
        return eventUpdateNotificationID;
    }

    public void setEventUpdateNotificationID(int eventUpdateNotificationID) {
        this.eventUpdateNotificationID = eventUpdateNotificationID;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
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
