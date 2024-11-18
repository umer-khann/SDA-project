package com.example.sdaproj;

import java.util.Date;
import java.util.List;

public class ConcertEvent extends Event {
    private List<String> performerNames;
    private String genre;

    public ConcertEvent(int eventID, String eventName, Date eventDate, double budget, List<String> performerNames, String genre) {
        super(eventID, eventName, eventDate, budget);
        this.performerNames = performerNames;
        this.genre = genre;
    }

    // Getters and setters
    public List<String> getPerformerNames() {
        return performerNames;
    }

    public String getGenre() {
        return genre;
    }
}
