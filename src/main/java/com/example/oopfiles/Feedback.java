package com.example.oopfiles;

import java.time.LocalDate;

public class Feedback {
    // Attributes
    private int feedbackID;
    private int rating; // Rating should be between 1 and 5
    private String comments;
    private LocalDate date;

    // Constructor
    public Feedback(int feedbackID, int rating, String comments, LocalDate date) {
        this.feedbackID = feedbackID;
        setRating(rating); // Use the setter to validate the rating
        this.comments = comments;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
}
