package com.example.oopfiles;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private NotificationObserver admin;
    private List<NotificationObserver> eventOrganizers = new ArrayList<>();
    private List<NotificationObserver> attendees = new ArrayList<>();

    // Add observer based on type
    public void addObserver(NotificationObserver observer, String userType) {
        switch (userType.toLowerCase()) {
            case "admin":
                admin = (NotificationObserver) UserFactory.createUser("ADMIN");
                break;
            case "eventorganizer":
                eventOrganizers.add(observer);
                break;
            case "attendee":
                attendees.add(observer);
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }

    // Remove observer based on type
    public void removeObserver(NotificationObserver observer, String userType) {
        switch (userType.toLowerCase()) {
            case "admin":
                break;
            case "eventorganizer":
                eventOrganizers.remove(observer);
                break;
            case "attendee":
                attendees.remove(observer);
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }

    // Notify all admins
    public void notifyAdmins(String message) {
            admin.receiveNotification(message);
    }

    // Notify all event organizers
    public void notifyEventOrganizers(String message) {
        for (NotificationObserver organizer : eventOrganizers) {
            organizer.receiveNotification(message);
        }
    }

    // Notify all attendees
    public void notifyAttendees(String message) {
        for (NotificationObserver attendee : attendees) {
            attendee.receiveNotification(message);
        }
    }
    public void notifyAl(String message){
        admin.receiveNotification(message);
        for (NotificationObserver organizer : eventOrganizers) {
            organizer.receiveNotification(message);
        }
        for (NotificationObserver attendee : attendees) {
            attendee.receiveNotification(message);
        }
    }
}
