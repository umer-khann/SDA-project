package com.example.JDBC;

import com.example.oopfiles.*;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SponsorshipDBHandler {

    public void showEvents(ObservableList<Sponsorship> sponsorshipList, int EventOrgID) {
        String query = "SELECT eventID, sponsorshipID, sponsorName, contributionAmount " +
                "FROM Sponsorship WHERE eventOrganizerID = ?";
        System.out.println(EventOrgID + " in too.");

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the EventOrgID parameter
            preparedStatement.setInt(1, EventOrgID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Retrieve data from the ResultSet
                    int eventID = resultSet.getInt("eventID");
                    int sponsorID = resultSet.getInt("sponsorID");
                    String sponsorName = resultSet.getString("sponsorName");
                    double contributionAmount = resultSet.getDouble("contributionAmount");

                    // Create a new Sponsorship object and populate it
                    Sponsorship sponsorship = new Sponsorship();
                    sponsorship.setEventID(eventID);
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


}
