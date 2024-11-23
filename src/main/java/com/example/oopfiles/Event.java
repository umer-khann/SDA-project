package com.example.oopfiles;

import com.example.JDBC.EventDBController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Event {
    private int eventID;
    private String eventName;
    private String eventDate;
    private boolean status;
    private double budget;
    protected static EventDBController db;
    private String eventType;
    private EventOrganizer organizer;  // Aggregation: Event has an organizer

    public Event() {
        db = new EventDBController();
    }


    public static void deleteEvent(int eventID) {
    }

    public static void updateEventDetails(Event selectedEvent) {
    }

    public static ObservableList<Event> initializeTableForOrganizer(ObservableList<Event> eventList, int currentOrganizerid) {
        // Initialize the EventDBController to get events from the database
        EventDBController db = new EventDBController();

        // Create a new list to hold events filtered by the organizer
        ObservableList<Event> filteredEvents = FXCollections.observableArrayList();

        // Fetch all complete events (from different event types)
        db.showCompleteEvents(eventList);  // This will fill eventList with all events from the DB

        // Now filter the events based on the currentOrganizerid
        for (Event event : eventList) {
            // Check if the event belongs to the current organizer by comparing the organizerID
            if (event.getOrganizerID() == currentOrganizerid) {
                filteredEvents.add(event);  // Add the event to the filtered list
            }
        }

        // Return the filtered list of events for the current organizer
        return filteredEvents;
    }



    public boolean updateDetails(String newName, String newDate) {
        this.eventName = newName;
        this.eventDate = newDate;
        this.eventType="";
        return true;
    }

    public static boolean checkEvent(int ID){
        db=new EventDBController();
        boolean result = db.verifyEvent(ID);
        return result;
    }

    public static void updateBudget(int ID,float newBudget){
        db=new EventDBController();
        db.updateEventBudget(ID,newBudget);
    }

    public static ObservableList<Event> intializeTable(ObservableList<Event> eventList,int EventOrgID){
        db=new EventDBController();
        db.showEvents(eventList,EventOrgID);
        return eventList;
    }






    public boolean allocateBudget(double newBudget) {
        if (newBudget >= 0) {
            this.budget = newBudget;
            return true;
        }
        return false;
    }

    public abstract boolean createEvent();
    // Getters and setters
    public int getEventID() {
        return eventID;
    }

    public void assignValues(int eventID,String eventName,float budget,String eventType){
        this.eventID=eventID;
        this.eventName=eventName;
        this.budget=budget;
        this.eventType=eventType;
    }


    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public boolean isActive() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void activate() {
        this.status = true;
    }

    public void deactivate() {
        this.status = false;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public EventOrganizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(EventOrganizer organizer) {
        this.organizer = organizer;
    }

    public void assignAllValues(int eventID, String eventName, float budget, String eventType, String eventDate, boolean status) {
        this.eventID=eventID;
        this.eventName=eventName;
        this.budget=budget;
        this.eventType=eventType;
        this.eventDate=eventDate;
        this.status=status;
    }

    public int getOrganizerID() {
        return organizer.getID(organizer.getUsername());
    }


    // Placeholder method for saving event to database
}
