package com.example.oopfiles;

import java.util.List;

public class NotificationService {
    private Admin admin;
    private User eventOrganizer;
    private User attendee;

    // Add observer based on type
    public void addObserver(User observer, String userType) {
        switch (userType.toLowerCase()) {
            case "admin":
                admin = new Admin();
                break;
            case "eventorganizer":
                eventOrganizer = new EventOrganizer();
                break;
            case "attendee":
                attendee = new Attendee() {
                    @Override
                    public String gettype() {
                        return "";
                    }
                    @Override
                    public boolean registerForEvent() {
                        return false;
                    }

                    @Override
                    public boolean provideFeedback() {
                        return false;
                    }

                    @Override
                    public boolean registerAttendee() {
                        return false;
                    }

                    @Override
                    public List<Notification> receiveNotification(String message) {
                        return List.of();
                    }
                };
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }

    // Remove observer based on type

    // Notify all admins
    public List<Notification> notifyAdmins(String message) {
        return admin.receiveNotification(message);
    }
    public List<Notification> notifyAdmins() {
        return admin.receiveNotification("");
    }

    // Notify all event organizers
    public List<Notification> notifyEventOrganizers(String message) {
        return eventOrganizer.receiveNotification(message);
    }

    // Notify all attendees
    public List<Notification> notifyAttendees(String message) {
        return attendee.receiveNotification(message);
    }
    public void notifyAl(String message){
        admin.receiveNotification(message);
        eventOrganizer.receiveNotification(message);
        attendee.receiveNotification(message);
    }
}
