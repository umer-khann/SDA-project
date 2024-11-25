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
    public String gettype(){
        return "GENERAL";
    }
    public String getAttendeeType() { return "General"; }

    @Override
    public boolean provideFeedback() {
        // Implementation for providing feedback
        return true;
    }

    @Override
    public boolean registerAttendee() {
        if(db.signUpAttendee(this)) {
            db.addNotification(this.userID, 3, "Account created for username: " + getUsername(), "Account creation");
            return true;
        }
        return false;
    }

    public void addNotification(int ID, int userType, String message, String notifType){
        db.addNotification(ID,userType,message,notifType);
    };

    // Specific Method for General Attendee
    public boolean redeemPoints() {
        // Implementation to redeem points
        return true;
    }

    // Specific Getter and Setter Methods
    public String getMembershipLevel() { return membershipLevel; }
    public void setMembershipLevel(String membershipLevel) { this.membershipLevel = membershipLevel; }
}
