package com.example.sdaproj;

public class Sponsorship {

    private String sponsorId;
    private String sponsorName;
    private double contributionAmount;

    // Constructor
    public Sponsorship(String sponsorId, String sponsorName, double contributionAmount) {
        this.sponsorId = sponsorId;
        this.sponsorName = sponsorName;
        this.contributionAmount = contributionAmount;
    }

    // Getters and setters
    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
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
    public boolean removeSponsorship() {
        //SponsorshipDAO dao = new SponsorshipDAO();
        //return dao.deleteSponsorship(this.sponsorId);
        return false;
    }
}
