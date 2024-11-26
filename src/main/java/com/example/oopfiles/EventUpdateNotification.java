package com.example.oopfiles;

public class EventUpdateNotification {
    private int eventUpdateNotificationID;
    private Integer eventID;
    private int userID;
    private String message;
    private String status;
    private String createdAt;

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
