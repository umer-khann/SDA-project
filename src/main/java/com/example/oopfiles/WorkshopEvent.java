package com.example.oopfiles;

import java.util.Date;


public class WorkshopEvent extends Event {
    private String topic;
    private double duration; // In hours
    private String instructor;

    public WorkshopEvent() {
        super();
    }
    @Override
    public boolean createEvent() {
        return db.saveWorkshopEvent(this, 1 ,1);
        // Event creation logic specific to ConcertEvent can go here
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

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public boolean createEvent(int ID, int I) {
        if(db.saveWorkshopEvent(this,ID,I)){
            db.EventCreatedNoti(this,ID);
            return true;
        }
        return false;
        // Event creation logic specific to ConcertEvent can go here
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
}
