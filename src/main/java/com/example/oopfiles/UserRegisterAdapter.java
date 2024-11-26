package com.example.oopfiles;

public class UserRegisterAdapter implements RegisterAdapter {
    private final User user;

    public UserRegisterAdapter(User user) {
        this.user = user;
    }

    @Override
    public boolean register() {

        // Check user type and call appropriate method
        if (user instanceof Attendee) {
            return ((Attendee) user).registerAttendee(); // Calls registerAttendee on Attendee subclasses
        } else if (user instanceof EventOrganizer) {
            ((EventOrganizer) user).registerEventOrganizer();
            return true; // Assume success for EventOrganizer
        }

        // If the user type is not supported, return false
        System.err.println("Unknown user type: Registration failed.");
        return false;
    }
}
