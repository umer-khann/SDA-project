package com.example.JDBC;

import com.example.oopfiles.*;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SponsorshipDBHandler {

    public void showEvents(ObservableList<Sponsorship> sponsorshipList, int EventOrgID) {
        String query = "SELECT s.eventID, e.eventName, s.sponsorshipID, s.sponsorName, s.contributionAmount " +
                "FROM Sponsorship s " +
                "JOIN Event e ON s.eventID = e.eventID " +
                "WHERE s.eventOrganizerID = ?";
        System.out.println(EventOrgID + " in too.");

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the EventOrgID parameter
            preparedStatement.setInt(1, EventOrgID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Retrieve data from the ResultSet
                    int eventID = resultSet.getInt("eventID");
                    String eventName = resultSet.getString("eventName");
                    int sponsorID = resultSet.getInt("sponsorshipID");
                    String sponsorName = resultSet.getString("sponsorName");
                    double contributionAmount = resultSet.getDouble("contributionAmount");

                    // Create a new Sponsorship object and populate it
                    Sponsorship sponsorship = new Sponsorship();
                    sponsorship.setEventID(eventID);
                    sponsorship.setEventName(eventName); // Assuming Sponsorship has this method
                    sponsorship.setSponsorID(sponsorID);
                    sponsorship.setSponsorName(sponsorName);
                    sponsorship.setContributionAmount(contributionAmount);

                    // Add the sponsorship to the list
                    sponsorshipList.add(sponsorship);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public boolean removeSponsor(int eventID, int sponsorshipID) {
        String checkQuery = "SELECT COUNT(*) FROM Sponsorship WHERE eventID = ? AND sponsorshipID = ?";
        String deleteQuery = "DELETE FROM Sponsorship WHERE eventID = ? AND sponsorshipID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
             PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {

            // Check if the specific eventID and sponsorshipID exist
            checkStmt.setInt(1, eventID);
            checkStmt.setInt(2, sponsorshipID);

            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // If the eventID and sponsorshipID exist, proceed to delete
                    deleteStmt.setInt(1, eventID);
                    deleteStmt.setInt(2, sponsorshipID);

                    int rowsDeleted = deleteStmt.executeUpdate();
                    return rowsDeleted > 0; // Return true if deletion was successful
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Return false if the IDs don't exist or an error occurs
    }

    public String getName(String sponsorID) {
        String sponsorName = null;
        String query = "SELECT sponsorName FROM Sponsorship WHERE sponsorshipID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, sponsorID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    sponsorName = resultSet.getString("sponsorName");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sponsorName; // Returns the name if found, or null if not
    }

}
