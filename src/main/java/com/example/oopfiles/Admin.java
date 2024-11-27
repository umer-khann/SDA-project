package com.example.oopfiles;

//import com.example.JDBC.AdminDBHandler;

import com.example.JDBC.AdminDBHandler;

import java.util.List;

public class Admin extends User implements NotificationObserver{
    private static final User INSTANCE = new Admin();

    private static final String HARD_CODED_USERNAME = "umer";
    private static final String HARD_CODED_PASSWORD = "umer";
    private static AdminDBHandler db = AdminDBHandler.getInstance();


    public Admin() {
        super(1,"Umer Khan","umer.2003@gmail.com","03005560602");
        this.setUserName(HARD_CODED_USERNAME);
        this.setPassword(HARD_CODED_PASSWORD);
    }

    public static User getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean login(String uname, String pass) {
        if (HARD_CODED_USERNAME.equals(uname) && HARD_CODED_PASSWORD.equals(pass)) {
            this.loggedIn = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean logout() {
        this.loggedIn = false;
        return true;
    }

    @Override
    public boolean isValidUserName() {
        return false;
    }

    @Override
    public boolean isValidEmail() {
        return false;
    }
    @Override
    public void addNotification(int ID, int userType, String message, String notifType){
        db.addNotification(ID,userType,message,notifType);
    }

    @Override
    public void Create(String username, String password) {

    }

    @Override
    public boolean registerAttendee() {
        return false;
    }

    @Override
    public List<Notification> receiveNotification(int message) {
        return db.retrieveNotifications();
    }

    @Override
    public List<EventUpdateNotification> receiveEvent(int s) {
        return db.retrieveEventUp();}

    @Override
    public List<EventRegistrationNotification> recieveEventReg(int s) {
        return db.retrieveEventReg();
    }

    @Override
    public List<TicketPurchaseNotification> receiveTicPur(int s) {
        return db.retrieveTicketPur();    }

    @Override
    public List<PaymentNotification> receivePayment(int s) {
        return db.retrievePaymentNo();
    }

    @Override
    public List<EventUpdateNotification> AttendeeEvUP(int attendeeid) {
        return List.of();
    }

    @Override
    public List<EventRegistrationNotification> AttendeeEventReg(int attendeeid) {
        return List.of();
    }

    @Override
    public List<TicketPurchaseNotification> TicketPurchase(int attendeeid) {
        return List.of();
    }

    @Override
    public List<PaymentNotification> PaymentNotif(int attendeeid) {
        return List.of();
    }

}
