package com.example.oopfiles;

import com.example.JDBC.EventAnalysisDBHandler;

import java.util.Date;
import java.util.List;


public class EventAnalysis {
    private int eventID;
    private String eventName;
    private double AvgRating;
    private int ticketsSold;
    private String venueName;
    private Date eventDate;
    private static EventAnalysisDBHandler db = EventAnalysisDBHandler.getInstance();
    public EventAnalysis(int eventID, String eventName, double avgRating, int ticketsSold, String venueName, Date eventDate) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.AvgRating = avgRating;
        this.ticketsSold = ticketsSold;
        this.venueName = venueName;
        this.eventDate = eventDate;
    }
    public EventAnalysis() {
    }
    public List<EventAnalysis> generateeventanalysis(List<EventAnalysis> eval){
        eval = db.generateEventAnalysis();
        return eval;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getAvgRating() {
        return AvgRating;
    }

    public void setAvgRating(double avgRating) {
        AvgRating = avgRating;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}