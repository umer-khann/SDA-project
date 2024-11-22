package com.example.oopfiles;

public class VipAttendee extends Attendee {
    private String VIPLevel;
    private String accessToExclusiveAreas;

    // Constructor
    public VipAttendee() {
        super();
        this.VIPLevel = VIPLevel;
        this.accessToExclusiveAreas = accessToExclusiveAreas;
    }

    @Override
    public boolean registerForEvent() {
        // Implementation for VIP Attendee registration
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


    // Specific Getter and Setter Methods
    public String getVIPLevel() { return VIPLevel; }
    public void setVIPLevel(String VIPLevel) { this.VIPLevel = VIPLevel; }
}
