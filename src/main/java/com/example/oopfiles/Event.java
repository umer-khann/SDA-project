package com.example.oopfiles;

import com.example.JDBC.EventDBController;

import java.util.Date;

public abstract class Event {
    private int eventID;
    private String eventName;
    private String eventDate;
    private boolean status;
    private double budget;
    protected EventDBController db;
    public Event() {
        db = new EventDBController();
    }

    public boolean updateDetails(String newName, String newDate) {
        this.eventName = newName;
        this.eventDate = newDate;
        return true;
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

    // Placeholder method for saving event to database
}
