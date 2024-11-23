package com.example.oopfiles;

import com.example.JDBC.SponsorshipDBHandler;
import javafx.collections.ObservableList;

public class Sponsorship {

    private int sponsorID;
    private int eventID;
    private String eventName;
    private String sponsorName;
    private double contributionAmount;
    SponsorshipDBHandler db;

    // Constructor
    public Sponsorship() {
        db=new SponsorshipDBHandler();
    }

    public ObservableList<Sponsorship> intializeTable(ObservableList<Sponsorship> sponsorshipList, int eventOrgID) {
        db.showEvents(sponsorshipList,eventOrgID);
        return sponsorshipList;
    }

    // Getters and setters
    public int getSponsorID() {
        return sponsorID;
    }

    public void setSponsorID(int sponsorId) {
        this.sponsorID = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public double getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(double contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    // Add sponsorship
    public boolean addSponsorship() {
        //SponsorshipDAO dao = new SponsorshipDAO();
        //return dao.saveSponsorship(this);
        return false;
    }

    // Remove sponsorship
    public boolean removeSponsorship(int eventID,int sponsorshipID) {
        boolean result=db.removeSponsor(eventID,sponsorshipID);
        return result;
    }


    public int getEventID() {
        return eventID;
    }
    public void setEventID(int eventID) {
        this.eventID=eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String retrieveName(String sponsorID) {
        String name = db.getName(sponsorID);
        return name;
    }
}
