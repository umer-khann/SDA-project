package com.example.oopfiles;

import com.example.JDBC.EventDBController;

import java.util.Date;
import java.util.List;

public class ConcertEvent extends Event {
    private String performer;
    private String genre;

    public ConcertEvent() {
        super();
    }

    // Getter and Setter for performerNames
    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    // Getter and Setter for genre
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean createEvent() {
        return db.saveConcertEvent(this,1,1);
        // Event creation logic specific to ConcertEvent can go here
    }
    public boolean createEvent(int ID, int I) {
         if(db.saveConcertEvent(this,ID,I)){
             db.EventCreatedNoti(this,ID);
             return true;
        }
         return false;
        // Event creation logic specific to ConcertEvent can go here
    }

    public void setPerformerName(String performerName) {
        this.performer=performerName;
    }

    public String getPerformerName() {
        return performer;
    }
}
