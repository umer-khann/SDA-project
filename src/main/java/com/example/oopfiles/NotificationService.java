package com.example.oopfiles;

import java.util.List;

public class NotificationService {
    private User admin;
    private User eventOrganizer;
    private User attendee;


    // Add observer based on type
    public void addObserver(User observer, String userType) {
        switch (userType.toUpperCase()) {
            case "ADMIN":
                admin = UserFactory.createUser("ADMIN");
                break;
            case "EVENT_ORGANIZER":
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
    public List<Notification> notifyAdmins(int message) {
        return admin.receiveNotification(message);
    }
    public List<EventUpdateNotification> notifyAdminsEvCr(){
        return admin.receiveEvent(0);
    }

    public List<EventRegistrationNotification> notifyAdminsEvUp(){
        return admin.recieveEventReg(0);
    }

    public List<EventUpdateNotification> notifyAtendeeEvUp(int attendeeid){
        return attendee.AttendeeEvUP(attendeeid);
    }

    public List<TicketPurchaseNotification> notifyAdminsTiPu(){
        return admin.receiveTicPur(0);
    }

    public List<PaymentNotification> notifyAdminsPaN(){
        return admin.receivePayment(0);
    }

    public List<Notification> notifyAdmins() {
        return admin.receiveNotification(0);
    }

    // Notify all event organizers
    public List<Notification> notifyEventOrganizers(int ID) {
        return eventOrganizer.receiveNotification(ID);
    }

    // Notify all attendees
    public List<Notification> notifyAttendees(int message) {
        return attendee.receiveNotification(message);
    }
    public void notifyAl(int message){
        admin.receiveNotification(message);
        eventOrganizer.receiveNotification(message);
        attendee.receiveNotification(message);
    }


    public List<EventRegistrationNotification> notifyEventReg(int attendeeid){
        return attendee.AttendeeEventReg(attendeeid);
    }


    public List<TicketPurchaseNotification> TicketPurchaseNotif(int attendeeid) {
        return attendee.TicketPurchase(attendeeid);
    }

    public List<PaymentNotification> PaymentNotif(int attendeeid) {
       return attendee.PaymentNotif(attendeeid);
    }



}
