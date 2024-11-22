package com.example.oopfiles;

import com.example.JDBC.VenueDBHandler;

// Indoor Venue Class
public class IndoorVenue extends Venue {
    private String roomNumber;
    private int floor;
    private VenueDBHandler db;

    // Constructor
    public IndoorVenue(String venueName, String location, int capacity, String roomNumber, int floor) {
        super(venueName, location, capacity);
        this.roomNumber = roomNumber;
        this.floor = floor;
        db=new VenueDBHandler();
    }

    @Override
    public void addVenue(int ID) {
        db.addVenue("Indoor",this,ID);
    }

    @Override
    public void removeVenue() {

    }

    // Implementation of abstract method
    @Override
    public void displayVenueDetails() {
        System.out.println("Indoor Venue Details:");
        System.out.println("Venue ID: " + getVenueId());
        System.out.println("Venue Name: " + getVenueName());
        System.out.println("Location: " + getLocation());
        System.out.println("Capacity: " + getCapacity());
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Floor: " + floor);
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getFloor() {
        return floor;
    }
}
