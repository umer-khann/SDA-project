package com.example.oopfiles;
import java.util.ArrayList;
import java.util.List;

public class EventOrganizer extends User {
    private List<String> eventsOrganized;
    private String experienceLevel;

    // Constructor
    public EventOrganizer() {
        super();
    }

    @Override
    public boolean login(String uname, String pass) {
        // Implementation for Event Organizer login
        this.loggedIn = true;
        System.out.println("Event Organizer " + name + " has logged in.");
        return loggedIn;
    }

    @Override
    public boolean logout() {
        // Implementation for Event Organizer logout
        this.loggedIn = false;
        System.out.println("Event Organizer " + name + " has logged out.");
        return !loggedIn;
    }

    // Specific Methods for EventOrganizer
    public void createEvent(String eventName) {
        // Implementation to create a new event
        eventsOrganized.add(eventName);
        System.out.println("Event '" + eventName + "' has been created.");
    }

    public void manageEvent(String eventName) {
        // Implementation to manage an existing event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Managing event: " + eventName);
            // Add additional management functionality here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void handleSponsorships(String eventName) {
        // Implementation to handle sponsorships for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Handling sponsorships for event: " + eventName);
            // Add sponsorship handling logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void handleTicketAndPayment(String eventName) {
        // Implementation to handle ticket sales and payments
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Handling ticket and payment for event: " + eventName);
            // Add ticket and payment handling logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void allocateEventResources(String eventName) {
        // Implementation to allocate resources for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Allocating resources for event: " + eventName);
            // Add resource allocation logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void addVenue(String eventName, String venue) {
        // Implementation to add a venue for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Venue '" + venue + "' added to event: " + eventName);
            // Add venue logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void removeVenue(String eventName, String venue) {
        // Implementation to remove a venue for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Venue '" + venue + "' removed from event: " + eventName);
            // Add logic to remove venue here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    public void handleEventBudget(String eventName, double budget) {
        // Implementation to handle the budget for an event
        if (eventsOrganized.contains(eventName)) {
            System.out.println("Budget of " + budget + " allocated for event: " + eventName);
            // Add budget handling logic here
        } else {
            System.out.println("Event '" + eventName + "' not found.");
        }
    }

    // Getters and Setters for EventOrganizer-specific fields
    public List<String> getEventsOrganized() { return eventsOrganized; }
    public void setEventsOrganized(List<String> eventsOrganized) { this.eventsOrganized = eventsOrganized; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
}
