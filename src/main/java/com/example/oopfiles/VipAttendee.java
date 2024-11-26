package com.example.oopfiles;

import java.util.List;

public class VipAttendee extends Attendee implements NotificationObserver{
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
        if(db.signUpAttendee(this)) {
            db.addNotification(this.userID, 3, "Account created for username: " + getUsername(), "Account creation");
            return true;
        }
        return false;
    }
    public String gettype(){
        return "VIP";
    }
    public void addNotification(int ID, int userType, String message, String notifType){
        db.addNotification(ID,userType,message,notifType);
    };

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints=loyaltyPoints;
    }
    public String getAttendeeType() { return "VIP"; }
    // Specific Getter and Setter Methods
    public String getVIPLevel() { return VIPLevel; }
    public void setVIPLevel(String VIPLevel) { this.VIPLevel = VIPLevel; }

    @Override
    public List<Notification> receiveNotification(String message) {
        return null;
    }

    @Override
    public List<EventUpdateNotification> receiveEvent(String s) {
        return List.of();
    }

    @Override
    public List<EventRegistrationNotification> recieveEventReg(String s) {
        return List.of();
    }

    @Override
    public List<TicketPurchaseNotification> receiveTicPur(String s) {
        return List.of();
    }

    @Override
    public List<PaymentNotification> receivePayment(String s) {
        return List.of();
    }

    public void setAccessToExclusiveAreas(String accessToExclusiveAreas) {
        this.accessToExclusiveAreas = accessToExclusiveAreas;
    }
}
