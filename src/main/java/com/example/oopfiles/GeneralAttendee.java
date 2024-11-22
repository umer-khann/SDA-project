package com.example.oopfiles;

public class GeneralAttendee extends Attendee {
    private String membershipLevel;

    // Constructor
    public GeneralAttendee() {
        super();
    }

    @Override
    public boolean registerForEvent() {
        // Implementation for General Attendee registration
        return true;
    }

    @Override
    public boolean provideFeedback() {
        // Implementation for providing feedback
        return true;
    }

    @Override
    public boolean registerAttendee() {
        db.signUpAttendee(this);
        return true;
    }

    public void addNotification(int ID, int userType, String message, String notifType){};

    // Specific Method for General Attendee
    public boolean redeemPoints() {
        // Implementation to redeem points
        return true;
    }

    // Specific Getter and Setter Methods
    public String getMembershipLevel() { return membershipLevel; }
    public void setMembershipLevel(String membershipLevel) { this.membershipLevel = membershipLevel; }
}
