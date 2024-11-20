package com.example.sdaproj;

// Indoor Venue Class
class IndoorVenue extends Venue {
    private String roomNumber;
    private int floor;

    // Constructor
    public IndoorVenue(String venueId, String venueName, String location, int capacity, String roomNumber, int floor) {
        super(venueId, venueName, location, capacity);
        this.roomNumber = roomNumber;
        this.floor = floor;
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
}
