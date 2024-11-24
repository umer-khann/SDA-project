package com.example.JDBC;

import com.example.oopfiles.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDBHandler {

    // Constructor
    public FeedbackDBHandler() {
        // No need to create a connection here, as we use MyJDBC for the connection
    }

    // Method to insert a new feedback
    public boolean insertFeedback(Feedback feedback) {
        String query = "INSERT INTO Feedback (rating, comments, date, eventID) VALUES (?, ?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, feedback.getRating());
            stmt.setString(2, feedback.getComments());
            stmt.setDate(3, new java.sql.Date(feedback.getDate().getTime()));
            stmt.setInt(4, feedback.getEventID());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve all feedback for a specific event
    public List<Feedback> getFeedbackForEvent(int eventID) {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM Feedback WHERE eventID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, eventID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int feedbackID = rs.getInt("feedbackID");
                int rating = rs.getInt("rating");
                String comments = rs.getString("comments");
                Date date = rs.getDate("date");
                feedbackList.add(new Feedback(feedbackID, rating, comments, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    // Method to update feedback
    public boolean updateFeedback(Feedback feedback) {
        String query = "UPDATE Feedback SET rating = ?, comments = ?, date = ?, eventID = ? WHERE feedbackID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, feedback.getRating());
            stmt.setString(2, feedback.getComments());
            stmt.setDate(3, new java.sql.Date(feedback.getDate().getTime()));
            stmt.setInt(4, feedback.getEventID());
            stmt.setInt(5, feedback.getFeedbackID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete feedback by ID
    public boolean deleteFeedback(int feedbackID) {
        String query = "DELETE FROM Feedback WHERE feedbackID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, feedbackID);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all feedback entries
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM Feedback";

        try (Connection connection = MyJDBC.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int feedbackID = rs.getInt("feedbackID");
                int rating = rs.getInt("rating");
                String comments = rs.getString("comments");
                Date date = rs.getDate("date");
                int eventID = rs.getInt("eventID");
                feedbackList.add(new Feedback(feedbackID, rating, comments, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackList;
    }
}
