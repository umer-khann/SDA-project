package com.example.JDBC;

import com.example.JDBC.MyJDBC;
import com.example.oopfiles.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VenueDBHandler {
    private static VenueDBHandler instance;
    public static synchronized VenueDBHandler getInstance() {
        if (instance == null) {
            instance = new VenueDBHandler();
        }
        return instance;
    }
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
    public static List<Venue> getAllVenues(int EVID) {
        List<Venue> venues = new ArrayList<>();
        String query = "SELECT * FROM Venue WHERE eventOrganizerID = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, EVID); // Set the eventOrganizerID parameter
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Venue venue;
                    if (rs.getString("venueType").equals("Indoor")) {
                        venue = VenueFactory.createVenue("INDOOR");
                    } else {
                        venue = VenueFactory.createVenue("OUTDOOR");
                    }

                    venue.setVenueId(rs.getInt("venueID"));
                    venue.setVenueName(rs.getString("venueName"));
                    venue.setLocation(rs.getString("location"));
                    venue.setCapacity(rs.getInt("capacity"));
                    venues.add(venue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return venues;
    }
    public static List<Venue> getAllVenues() {
        List<Venue> venues = new ArrayList<>();
        String query = "SELECT * FROM Venue";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Venue venue;
                    if (rs.getString("venueType").equals("Indoor")) {
                        venue = VenueFactory.createVenue("INDOOR");
                    } else {
                        venue = VenueFactory.createVenue("OUTDOOR");
                    }

                    venue.setVenueId(rs.getInt("venueID"));
                    venue.setVenueName(rs.getString("venueName"));
                    venue.setLocation(rs.getString("location"));
                    venue.setCapacity(rs.getInt("capacity"));
                    venues.add(venue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return venues;
    }
    public boolean deleteVenueNotifiction(int venueID) {
        String selectQuery = "SELECT ea.attendeeID, e.eventName FROM event e " +
                "JOIN eventattendee ea ON e.eventID = ea.eventID " +
                "WHERE e.venueID = ?";
        String insertQuery = "INSERT INTO eventupdatenotification (UserID, Message, Status) " +
                "VALUES (?, ?, 'Unread')";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Set the venueID for the SELECT query
            selectStmt.setInt(1, venueID);

            // Execute SELECT query to retrieve the attendee IDs and event names
            ResultSet rs = selectStmt.executeQuery();

            // Loop through the results and insert notifications for each attendee
            while (rs.next()) {
                int attendeeID = rs.getInt("attendeeID");
                String eventName = rs.getString("eventName");

                // Set the parameters for the INSERT query
                insertStmt.setInt(1, attendeeID);         // Set EventID (using venueID as EventID from event)
                insertStmt.setString(2, "Event Cancelled: " + eventName);      // Set UserID (attendeeID)

                // Execute the INSERT query for each attendee
                insertStmt.executeUpdate();
            }

            return true;  // Return true if insertions are successful
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }

    public boolean deleteVenue(int venueID, int EVID) {
        // SQL query for deleting the venue from the Venue table
        deleteVenueNotifiction(venueID);
        String deleteVenueQuery = "DELETE FROM Venue WHERE venueID = ? AND eventOrganizerID = ?";

        try (Connection connection = MyJDBC.getConnection()) {
            connection.setAutoCommit(false);

            // Step 1: Delete from the Venue table
            try (PreparedStatement venueStatement = connection.prepareStatement(deleteVenueQuery)) {
                venueStatement.setInt(1, venueID);
                venueStatement.setInt(2, EVID); // Ensure you set both parameters here
                int rowsAffected = venueStatement.executeUpdate();
                if (rowsAffected == 0) {
                    connection.rollback();
                    return false; // No rows deleted in the Venue table
                }
            }

            // Step 2: Commit transaction if deletion is successful
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean doesVenueExist(int venueID) {
        String query = "SELECT COUNT(*) FROM Venue WHERE venueID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, venueID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }

}
