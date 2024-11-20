package com.example.sdaproj;

// Outdoor Venue Class
class OutdoorVenue extends Venue {
    private String weatherPreparedness;
    private int additionalCapacity;

    // Constructor
    public OutdoorVenue(String venueId, String venueName, String location, int capacity, String weatherPreparedness, int additionalCapacity) {
        super(venueId, venueName, location, capacity);
        this.weatherPreparedness = weatherPreparedness;
        this.additionalCapacity = additionalCapacity;
    }

    // Implementation of abstract method
    @Override
    public void displayVenueDetails() {
        System.out.println("Outdoor Venue Details:");
        System.out.println("Venue ID: " + getVenueId());
        System.out.println("Venue Name: " + getVenueName());
        System.out.println("Location: " + getLocation());
        System.out.println("Capacity: " + getCapacity());
        System.out.println("Weather Preparedness: " + weatherPreparedness);
        System.out.println("Additional Capacity: " + additionalCapacity);
    }
}
