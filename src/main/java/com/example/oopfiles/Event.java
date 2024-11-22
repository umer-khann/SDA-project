package com.example.oopfiles;

import com.example.JDBC.EventDBController;
import javafx.collections.ObservableList;

public abstract class Event {
    private int eventID;
    private String eventName;
    private String eventDate;
    private boolean status;
    private double budget;
    protected static EventDBController db;
    private String eventType;
    public Event() {
        db = new EventDBController();
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

    public static ObservableList<Event> intializeTable(ObservableList<Event> eventList){
        db=new EventDBController();
        db.showEvents(eventList);
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

    // Placeholder method for saving event to database
}
