package com.example.sdaproj;

import java.util.Date;
import java.util.List;

public abstract class Event {
    private int eventID;
    private String eventName;
    private Date eventDate;
    private boolean status;
    private double budget;

    public Event(int eventID, String eventName, Date eventDate, double budget) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.budget = budget;
        this.status = false; // Default to inactive
    }

    public boolean updateDetails(String newName, Date newDate) {
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

    // Getters and setters
    public int getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public double getBudget() {
        return budget;
    }

    public boolean isActive() {
        return status;
    }

    public void activate() {
        this.status = true;
    }

    public void deactivate() {
        this.status = false;
    }
}


