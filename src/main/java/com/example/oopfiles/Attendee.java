package com.example.oopfiles;

import com.example.JDBC.AttendeeDBController;
import com.example.JDBC.AttendeeDBController;

import java.util.List;

public abstract class Attendee extends User implements NotificationObserver{
    protected int loyaltyPoints;
    protected static AttendeeDBController db = new AttendeeDBController();
    public abstract List<Notification> receiveNotification(String message);

    // Constructor
    Attendee() {
        super();
    }

    public static void addNotif(int ID, int userType, String message, String notifType) {
        db.addNotification(ID,userType,message,notifType);
    }

    public static boolean removeAttendee(int ID) {
        boolean check=db.removeAttendee(ID);
        return check;
    }

    public static boolean updateAttendee(int attendeeID, String name, String email, String contact, Integer loyaltyPoints) {
        boolean result = db.updateAttendee(attendeeID,name,email,contact,loyaltyPoints);
        return result;
    }

    public String toString() {
        return "Attendee {" +
                "attendeeID=" + getUserID() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", username='" + getUserName() + '\'' +
                '}';
    }
    public void Create(String uname, String pass){
        db.AssignAttendee(this, uname, pass);
    }
    @Override
    public boolean login(String uname, String pass) {

        boolean f = db.validateLogin(uname, pass);
        if(f == true){
            this.Create(uname, pass);
        }

        System.out.println(this);
        return f;
    }
    public boolean isValidUserName() {

        // Check if the username already exists
        if (db.usernameExists(this.getUserName())) {
            System.out.println("Username already exists!");
            return false;
        }

        return true;
    }
    public abstract String gettype();
    public boolean isValidEmail() {

        // Check if the email already exists
        if (db.emailExists(this.email)) {
            System.out.println("Email already exists!");
            return false;
        }

        return true;
    }

    @Override
    public boolean logout() {
        // Implementation for Event Organizer logout
        this.loggedIn = false;
        System.out.println("Event Organizer " + name + " has logged out.");
        return !loggedIn;
    }
    // Abstract Methods to be implemented by subclasses
    public abstract boolean registerForEvent();
    public abstract boolean provideFeedback();
    public abstract boolean registerAttendee();

    // Common Getter and Setter Methods
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }
    public int getAttendeeID() { return userID; }
    public String getAttendeeName() { return name; }
    public String getAttendeeEmail() { return email; }
    public String getAttendeeContact() { return contactDetails; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
}