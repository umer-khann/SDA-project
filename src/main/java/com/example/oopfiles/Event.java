package com.example.oopfiles;

import com.example.JDBC.EventDBController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public abstract class Event {
    private int eventID;
    private String eventName;
    private String eventDate;
    private boolean status;
    private double budget;
    protected static EventDBController db;
    private String eventType;
    private EventOrganizer organizer;  // Aggregation: Event has an organizer
    private static ObservableList<Attendee> attendeeList;

    public Event() {
        db = new EventDBController();
        attendeeList = FXCollections.observableArrayList();
    }


    public static void deleteEvent(int eventID) {
    }

    public static void updateEventDetails(Event selectedEvent) {
    }

    public static ObservableList<Event> initializeTableForOrganizer(ObservableList<Event> eventList, int currentOrganizerid) {
        db=new EventDBController();
        db.showCompleteEvents(eventList,currentOrganizerid);
        return eventList;
    }

    public static boolean updateEvent(int eventID, String newName, Date newDate)
    {
        db=new EventDBController();
        return db.updateEvent(eventID,newName,newDate);
    }

    public static void insertEventUpdateNotification(int eventID, int organizerUserID, String message) {
        db=new EventDBController();
        db.insertEventUpdateNotification(eventID,organizerUserID,message);
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
    public static ObservableList<Attendee> displayAttendees(int eventID){
        db=new EventDBController();
        db.retrieveAttendees(attendeeList,eventID);
        return attendeeList;
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

    public ObservableList<Attendee> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(ObservableList<Attendee> attendeeList) {
        this.attendeeList = attendeeList;
    }


    // Placeholder method for saving event to database
}
