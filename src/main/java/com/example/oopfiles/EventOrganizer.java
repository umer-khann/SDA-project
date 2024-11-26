package com.example.oopfiles;
import com.example.JDBC.EventOrganizerDBHandler;
import javafx.collections.ObservableList;

import java.util.List;

public class EventOrganizer extends User implements NotificationObserver{
    private List<String> eventsOrganized;
    private int experienceLevel;
    static EventOrganizerDBHandler dbHandler;


    private int eventid;
    private String eventname;
    private String name;
    int id;


    // Constructor
    public EventOrganizer() {
        super();
        dbHandler=new EventOrganizerDBHandler();
    }

    public static ObservableList<EventOrganizer> showEvents(ObservableList<EventOrganizer> eventList) {
        dbHandler = new EventOrganizerDBHandler();
        dbHandler.showEvents(eventList);
        return eventList;
    }


    public String toString() {
        return "EventOrganizer {" +
                "User ID: " + this.getUserID() + ", " +
                "Username: " + this.getUserName() + ", " +
                "Email: " + this.getEmail() + ", " +
                "Events Organized: " + (eventsOrganized != null ? eventsOrganized.toString() : "None") + ", " +
                "Experience Level: " + experienceLevel + ", " +
                "Logged In: " + loggedIn +
                " }";
    }


    public void addNotification(int ID, int userType, String message, String notifType){
        dbHandler.addNotification(ID,userType,message,notifType);
    };

//    public String getExperienceLevel(){ return experienceLevel;}
    @Override
    public void Create(String uname, String pass){
        dbHandler.assignEventOrganizer(this, uname, pass);
    }
    public boolean login(String u, String p) {

        boolean f = dbHandler.validateLogin(u, p);
        if(f == true){
            this.Create(u, p);
        }
        System.out.println(this);
        return f;
    }

    public static boolean RemoveEventOrganizer(int organizerId, Integer newOrganizerId) {
       return dbHandler.RemoveEventOrganizer(organizerId,newOrganizerId);
    }

    @Override
    public boolean logout() {
        // Implementation for Event Organizer logout
        this.loggedIn = false;
        System.out.println("Event Organizer " + name + " has logged out.");
        return !loggedIn;
    }

    // Specific Methods for EventOrganizer
    public void createEvent(String eventName) {
        // Implementation to create a new event
        eventsOrganized.add(eventName);
        System.out.println("Event '" + eventName + "' has been created.");
    }




    public void manageEvent(String eventName) {
        // Implementation to manage an existing event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Managing event: " + eventName);
            // Add additional management functionality here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void handleSponsorships(String eventName) {
        // Implementation to handle sponsorships for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Handling sponsorships for event: " + eventName);
            // Add sponsorship handling logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void handleTicketAndPayment(String eventName) {
        // Implementation to handle ticket sales and payments
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Handling ticket and payment for event: " + eventName);
            // Add ticket and payment handling logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void allocateEventResources(String eventName) {
        // Implementation to allocate resources for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Allocating resources for event: " + eventName);
            // Add resource allocation logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void addVenue(String eventName, String venue) {
        // Implementation to add a venue for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Venue '" + venue + "' added to event: " + eventName);
            // Add venue logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void removeVenue(String eventName, String venue) {
        // Implementation to remove a venue for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Venue '" + venue + "' removed from event: " + eventName);
            // Add logic to remove venue here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void handleEventBudget(String eventName, double budget) {
        // Implementation to handle the budget for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Budget of " + budget + " allocated for event: " + eventName);
            // Add budget handling logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public Integer getEventid() {
        return eventid;
    }

    public String getEventname() {
        return eventname;
    }


    // Setter for name
    public void setName(String name) {
        this.name = name;
    }
    public void setid(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean registerAttendee() {
        return false;
    }


    public int getID(String username) {
        int OrganizerID = dbHandler.getEventOrganizerID(username);
        return OrganizerID;
    }

    public boolean isValidUserName() {

        // Check if the username already exists
        if (dbHandler.usernameExists(this.getUserName())) {
            System.out.println("Username already exists!");
            return false;
        }

        return true;
    }


    public boolean isValidEmail() {

        // Check if the email already exists
        if (dbHandler.emailExists(this.email)) {
            System.out.println("Email already exists!");
            return false;
        }

        return true;
    }

    public void registerEventOrganizer() {
        dbHandler.signUpEventOrganizer(this);
    }


    // Getters and Setters for EventOrganizer-specific fields
    public List<String> getEventsOrganized() { return eventsOrganized; }
    public void setEventsOrganized(List<String> eventsOrganized) { this.eventsOrganized = eventsOrganized; }

    public int getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(int experienceLevel) { this.experienceLevel = experienceLevel; }

    public void addEventDetails(Integer eventID, String eventName) {
        this.eventid=eventID;
        this.eventname=eventName;
    }

    public int getOrganizerID() {
        return this.userID;
    }


    @Override
    public List<Notification> receiveNotification(int message) {
        return dbHandler.recieveNotifications(message);
    }

    @Override
    public List<EventUpdateNotification> receiveEvent(int s) {
        return List.of();
    }

    @Override
    public List<EventRegistrationNotification> recieveEventReg(int s) {
        return List.of();
    }

    @Override
    public List<TicketPurchaseNotification> receiveTicPur(int s) {
        return List.of();
    }

    @Override
    public List<PaymentNotification> receivePayment(int s) {
        return List.of();
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
}
