package com.example.oopfiles;

public class VenueFactory {


    // Factory method to create a Venue with parameters
    public static Venue createVenue(String venueType, String venueName, String location, int capacity, String additionalInfo, int extraValue) {
        switch (venueType.toUpperCase()) {
            case "INDOOR":
                return new IndoorVenue(venueName, location, capacity, additionalInfo, extraValue);
            case "OUTDOOR":
                return new OutdoorVenue(venueName, location, capacity, additionalInfo, extraValue);
            default:
                throw new IllegalArgumentException("Invalid venue type: " + venueType);
        }
    }

    // Factory method to create a Venue using its unparameterized constructor
    public static Venue createVenue(String venueType) {
        switch (venueType.toUpperCase()) {
            case "INDOOR":
                return new IndoorVenue();
            case "OUTDOOR":
                return new OutdoorVenue();
            default:
                throw new IllegalArgumentException("Invalid venue type: " + venueType);
        }
    }
}
