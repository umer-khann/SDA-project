package com.example.JDBC;

import com.example.JDBC.MyJDBC;
import com.example.oopfiles.EventOrganizer;
import com.example.oopfiles.IndoorVenue;
import com.example.oopfiles.OutdoorVenue;
import com.example.oopfiles.Venue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VenueDBHandler {
    public boolean addVenue(String type, Venue venue,int ID) {
        // SQL query to insert into Venue table
        String venueQuery = "INSERT INTO Venue (venueName, location, capacity, venueType, eventOrganizerID) VALUES (?, ?, ?, ?, ?)";
        String specificVenueQuery = "";

        // Prepare SQL for specific venue type (Indoor or Outdoor)
        if ("Indoor".equalsIgnoreCase(type)) {
            specificVenueQuery = "INSERT INTO IndoorVenue (indoorVenueID, roomNumber, floor) VALUES (?, ?, ?)";
        } else if ("Outdoor".equalsIgnoreCase(type)) {
            specificVenueQuery = "INSERT INTO OutdoorVenue (outdoorVenueID, weatherPreparedness, additionalCapacity) VALUES (?, ?, ?)";
        } else {
            return false; // Invalid type
        }

        try (Connection connection = MyJDBC.getConnection()) {
            // Start a transaction
            connection.setAutoCommit(false);

            // Step 1: Insert into the Venue table
            try (PreparedStatement preparedStatement = connection.prepareStatement(venueQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, venue.getVenueName());
                preparedStatement.setString(2, venue.getLocation());
                preparedStatement.setInt(3, venue.getCapacity());
                preparedStatement.setString(4, type);
                preparedStatement.setInt(5, ID);

                int venueRowsAffected = preparedStatement.executeUpdate();

                if (venueRowsAffected == 0) {
                    connection.rollback();
                    return false;
                }

                // Get the generated venueID for further insert into specific venue table
                int venueID = 0;
                try (var rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        venueID = rs.getInt(1);
                    }
                }

                // Step 2: Insert into the specific venue table (Indoor or Outdoor)
                if ("Indoor".equalsIgnoreCase(type)) {
                    try (PreparedStatement indoorPreparedStatement = connection.prepareStatement(specificVenueQuery)) {
                        indoorPreparedStatement.setInt(1, venueID);
                        indoorPreparedStatement.setString(2, ((IndoorVenue) venue).getRoomNumber());
                        indoorPreparedStatement.setInt(3, ((IndoorVenue) venue).getFloor());

                        int indoorRowsAffected = indoorPreparedStatement.executeUpdate();
                        if (indoorRowsAffected == 0) {
                            connection.rollback();
                            return false;
                        }
                    }
                } else if ("Outdoor".equalsIgnoreCase(type)) {
                    try (PreparedStatement outdoorPreparedStatement = connection.prepareStatement(specificVenueQuery)) {
                        outdoorPreparedStatement.setInt(1, venueID);
                        outdoorPreparedStatement.setString(2, ((OutdoorVenue) venue).getWeatherPreparedness());
                        outdoorPreparedStatement.setInt(3, ((OutdoorVenue) venue).getAdditionalCapacity());

                        int outdoorRowsAffected = outdoorPreparedStatement.executeUpdate();
                        if (outdoorRowsAffected == 0) {
                            connection.rollback();
                            return false;
                        }
                    }
                }

                // Step 3: Commit transaction if everything is successful
                connection.commit();
                return true;
            } catch (SQLException e) {
                // If any exception occurs, rollback the transaction
                connection.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
