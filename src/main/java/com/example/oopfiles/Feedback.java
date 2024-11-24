package com.example.oopfiles;

import com.example.JDBC.FeedbackDBHandler;

import java.time.LocalDate;
import java.util.Date;

public class Feedback {
    // Attributes
    private int feedbackID;
    private int rating; // Rating should be between 1 and 5
    private String comments;
    private Date date;
    private int eventID;
    private FeedbackDBHandler db;
    // Constructor
    public Feedback(int feedbackID, int rating, String comments, Date date) {
        this.feedbackID = feedbackID;
        setRating(rating); // Use the setter to validate the rating
        this.comments = comments;
        this.date = date;
        this.db = new FeedbackDBHandler();
    }
    public Feedback() {
        this.db = new FeedbackDBHandler();
    }

    // Getters and Setters
    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Method to submit feedback
    public boolean submitFeedback() {
        if (rating >= 1 && rating <= 5 && comments != null && !comments.isEmpty()) {
            System.out.println("Feedback submitted successfully:");
            displayFeedback();
            return true;
        } else {
            System.out.println("Feedback submission failed. Ensure all fields are valid.");
            return false;
        }
    }

    // Method to display feedback details
    public void displayFeedback() {
        System.out.println("Feedback ID: " + feedbackID);
        System.out.println("Rating: " + rating);
        System.out.println("Comments: " + comments);
        System.out.println("Date: " + date);
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public boolean insertFeedback() {
        return db.insertFeedback(this);
    }
}
