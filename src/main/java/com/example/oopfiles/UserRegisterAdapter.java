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
            if(user instanceof GeneralAttendee)
                return ((Attendee) user).registerAttendee("General");
            else
                return ((Attendee)user).registerAttendee("VIP");// Calls registerAttendee on Attendee subclasses
        } else if (user instanceof EventOrganizer) {
            ((EventOrganizer) user).registerEventOrganizer();
            return true; // Assume success for EventOrganizer
        }

        // If the user type is not supported, return false
        System.err.println("Unknown user type: Registration failed.");
        return false;
    }@Override
    public boolean register(String val) {

        // Check user type and call appropriate method
        if (user instanceof Attendee) {
            if(user instanceof GeneralAttendee)
                return ((Attendee) user).registerAttendee(val);
            else
                return ((Attendee)user).registerAttendee(val);// Calls registerAttendee on Attendee subclasses
        } else if (user instanceof EventOrganizer) {
            ((EventOrganizer) user).registerEventOrganizer();
            return true; // Assume success for EventOrganizer
        }

        // If the user type is not supported, return false
        System.err.println("Unknown user type: Registration failed.");
        return false;
    }
}
