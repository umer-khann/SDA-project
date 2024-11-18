package com.example.sdaproj;

import java.util.Date;

public class WorkshopEvent extends Event {
    private String topic;
    private double duration; // In hours
    private String instructor;

    public WorkshopEvent(int eventID, String eventName, Date eventDate, double budget, String topic, double duration, String instructor) {
        super(eventID, eventName, eventDate, budget);
        this.topic = topic;
        this.duration = duration;
        this.instructor = instructor;
    }

    // Getters and setters
    public String getTopic() {
        return topic;
    }

    public double getDuration() {
        return duration;
    }

    public String getInstructor() {
        return instructor;
    }
}
