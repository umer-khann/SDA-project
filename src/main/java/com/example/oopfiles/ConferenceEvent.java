package com.example.oopfiles;

import java.util.Date;
import java.util.List;

public class ConferenceEvent extends Event {
    private String speaker;
    private String agenda;

    public ConferenceEvent() {
        super();
    }


    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    @Override
    public boolean createEvent() {
        return db.saveConferenceEvent(this,1,1);
        // Event creation logic specific to ConcertEvent can go here
    }
    public boolean createEvent(int ID, int I) {
        if(db.saveConferenceEvent(this,ID,I)){
            db.EventCreatedNoti(this,ID);
            return true;
        }
        return false;
        // Event creation logic specific to ConcertEvent can go here
    }
    public void setSpeakerName(String speakerName) {
        this.speaker=speakerName;
    }

    public String getEventAgenda() {
        return agenda;
    }
}
