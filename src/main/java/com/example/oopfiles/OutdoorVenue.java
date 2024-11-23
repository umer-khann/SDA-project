package com.example.oopfiles;

import com.example.JDBC.VenueDBHandler;

// Outdoor Venue Class
public class OutdoorVenue extends Venue {
    private String weatherPreparedness;
    private int additionalCapacity;
    private VenueDBHandler db;

    // Constructor
    public OutdoorVenue(String venueName, String location, int capacity, String weatherPreparedness, int additionalCapacity) {
        super(venueName, location, capacity);
        this.weatherPreparedness = weatherPreparedness;
        this.additionalCapacity = additionalCapacity;
        db=new VenueDBHandler();
    }

    @Override
    public void addVenue(int ID) {
        db.addVenue("Outdoor",this,ID);
    }

    @Override
    public void removeVenue() {

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

    public String getWeatherPreparedness() {
        return weatherPreparedness;
    }

    public int getAdditionalCapacity() {
        return additionalCapacity;
    }
}
