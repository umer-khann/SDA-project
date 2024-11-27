package com.example.oopfiles;

import com.example.JDBC.VenueDBHandler;

import java.util.List;

public abstract class Venue {
    private int venueId;
    private String venueName;
    private String location;
    private int capacity;
    protected VenueDBHandler db = VenueDBHandler.getInstance();
    public Venue(String venueName, String location, int capacity) {
        this.venueId = 0;
        this.venueName = venueName;
        this.location = location;
        this.capacity = capacity;
    }
    public Venue() {

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

    public abstract void setDb(VenueDBHandler venueDBHandler);

    public List<Venue> getAllVenues(int evOrgID) {
        if (db == null) {
            throw new IllegalStateException("VenueDBHandler is not initialized!");
        }
        return db.getAllVenues(evOrgID);
    }
    public List<Venue> getAllVenues() {
        if (db == null) {
            throw new IllegalStateException("VenueDBHandler is not initialized!");
        }
        return db.getAllVenues();
    }
    public boolean venueExists(int venueId) {
        if (db == null) {
            throw new IllegalStateException("VenueDBHandler is not initialized!");
        }
        return db.doesVenueExist(venueId);
    }
    public boolean deleteVenue(int VenID,int evID){
        return db.deleteVenue(VenID,evID);
    }
}
