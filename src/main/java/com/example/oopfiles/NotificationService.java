package com.example.oopfiles;

import java.util.List;

public class NotificationService {
    private User admin;
    private User eventOrganizer;
    private User attendee;

    // Add observer based on type
    public void addObserver(User observer, String userType) {
        switch (userType.toLowerCase()) {
            case "admin":
                admin = UserFactory.createUser("ADMIN");
                break;
            case "eventorganizer":
                eventOrganizer = UserFactory.createUser("EVENT_ORGANIZER");
                break;
                case "GENERAL_ATTENDEE":
                attendee = UserFactory.createUser("GENERAL_ATTENDEE");
                break;
                case "VIP_ATTENDEE":
                attendee = UserFactory.createUser("VIP_ATTENDEE");
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
