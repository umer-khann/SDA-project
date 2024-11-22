package com.example.oopfiles;

// Abstract Class Venue
public abstract class Venue {
    private int venueId;
    private String venueName;
    private String location;
    private int capacity;

    // Constructor
    public Venue(String venueName, String location, int capacity) {
        this.venueId = 0;
        this.venueName = venueName;
        this.location = location;
        this.capacity = capacity;
    }

    // Getters and Setters for common attributes
    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Common methods
    public abstract void addVenue(int ID) ;

    public abstract void removeVenue();

    // Abstract method to be implemented by subclasses
    public abstract void displayVenueDetails();
}
