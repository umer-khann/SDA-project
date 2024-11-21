package com.example.oopfiles;

// Abstract Class Venue
abstract class Venue {
    private String venueId;
    private String venueName;
    private String location;
    private int capacity;

    // Constructor
    public Venue(String venueId, String venueName, String location, int capacity) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.location = location;
        this.capacity = capacity;
    }

    // Getters and Setters for common attributes
    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
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
    public void addVenue() {
        System.out.println("Adding Venue: " + venueName);
    }

    public void removeVenue() {
        System.out.println("Removing Venue: " + venueName);
    }

    // Abstract method to be implemented by subclasses
    public abstract void displayVenueDetails();
}
