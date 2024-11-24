package com.example.oopfiles;

// Updated Factory Class
public class UserFactory {
    public static User createUser(String userType) {
        switch (userType.toUpperCase()) {
            case "ADMIN":
                return Admin.getInstance(); // Singleton pattern for Admin
            case "GENERAL_ATTENDEE":
                return new GeneralAttendee();
            case "VIP_ATTENDEE":
                return new VipAttendee();
            case "EVENT_ORGANIZER":
                return new EventOrganizer(); // Create Event Organizer
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }
}

