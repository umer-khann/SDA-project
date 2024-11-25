package com.example.oopfiles;

public class Notification {
    private int notificationID;
    private int userID;
    private String userType;
    private String message;
    private String notificationType;
    private String createdAt;
    private int eventID;

    public Notification(int notificationID, int userID, String userType, String message, String notificationType, String createdAt, int eventID) {
        this.notificationID = notificationID;
        this.userID = userID;
        this.userType = userType;
        this.message = message;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
        this.eventID = eventID;
    }

    // Getters and setters
    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
