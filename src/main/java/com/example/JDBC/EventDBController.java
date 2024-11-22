package com.example.JDBC;

import com.example.oopfiles.ConcertEvent;
import com.example.oopfiles.ConferenceEvent;
import com.example.oopfiles.Event;
import com.example.oopfiles.WorkshopEvent;

import java.sql.*;
import java.util.Date;

public class EventDBController {

    /**
     * Save a new event to the database.
     *
     * @param event The event object to be saved.
     * @return true if the event is saved successfully, false otherwise.
     */
    public boolean saveEvent(Event event) {
        String query = "INSERT INTO Events (eventName, eventDate, budget, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, event.getEventName());
            preparedStatement.setString(2, event.getEventDate());
            preparedStatement.setDouble(3, event.getBudget());
            preparedStatement.setBoolean(4, event.isActive());

            int result = preparedStatement.executeUpdate();
            return result > 0; // Return true if at least one row is affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the details of an existing event in the database.
     *
     * @param eventID The ID of the event to be updated.
     * @param newName The new name of the event.
     * @param newDate The new date of the event.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updateEventDetails(int eventID, String newName, Date newDate) {
        String query = "UPDATE Events SET eventName = ?, eventDate = ? WHERE eventID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setDate(2, new java.sql.Date(newDate.getTime()));
            preparedStatement.setInt(3, eventID);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Return true if at least one row is affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete an event from the database by its ID.
     *
     * @param eventID The ID of the event to be deleted.
     * @return true if the deletion is successful, false otherwise.
     */
    public boolean deleteEvent(int eventID) {
        String query = "DELETE FROM Events WHERE eventID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, eventID);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Return true if at least one row is affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fetch an event from the database by its ID.
     *
     * @param eventID The ID of the event to be fetched.
     * @return An Event object if found, null otherwise.
     */
    public Event getEventByID(int eventID) {
        String query = "SELECT * FROM Events WHERE eventID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, eventID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Create an Event object with retrieved data
                    String eventName = resultSet.getString("eventName");
                    Date eventDate = resultSet.getDate("eventDate");
                    double budget = resultSet.getDouble("budget");
                    boolean status = resultSet.getBoolean("status");

                    // Return an Event object (assuming you have a concrete implementation of Event)
                    // You might need to use a concrete subclass of Event, depending on your design
                    return new ConcertEvent();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null; // Return null if no event is found
    }
    public boolean saveConcertEvent(ConcertEvent event, int eventOrganizerID, int venueID) {
        String query = "INSERT INTO Event (eventName, eventDate, budget, status, eventType, eventOrganizerID, venueID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Insert into Event table
            preparedStatement.setString(1, event.getEventName());
            preparedStatement.setString(2, event.getEventDate());
            preparedStatement.setDouble(3, event.getBudget());
            preparedStatement.setBoolean(4, event.isActive());
            preparedStatement.setString(5, "Concert");
            preparedStatement.setInt(6, eventOrganizerID);
            preparedStatement.setInt(7, venueID);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Get the generated eventID for the new event
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int eventID = generatedKeys.getInt(1);

                        // Now insert specific ConcertEvent details
                        String concertQuery = "INSERT INTO ConcertEvent (eventID, genre) VALUES (?, ?)";
                        try (PreparedStatement concertStmt = connection.prepareStatement(concertQuery)) {
                            concertStmt.setInt(1, eventID);
                            concertStmt.setString(2, event.getGenre());
                            int concertResult = concertStmt.executeUpdate();
                            return concertResult > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean saveWorkshopEvent(WorkshopEvent event, int eventOrganizerID, int venueID) {
        String query = "INSERT INTO Event (eventName, eventDate, budget, status, eventType, eventOrganizerID, venueID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Insert into Event table
            preparedStatement.setString(1, event.getEventName());
            preparedStatement.setString(2, event.getEventDate());
            preparedStatement.setDouble(3, event.getBudget());
            preparedStatement.setBoolean(4, event.isActive());
            preparedStatement.setString(5, "Workshop");
            preparedStatement.setInt(6, eventOrganizerID);
            preparedStatement.setInt(7, venueID);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Get the generated eventID for the new event
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int eventID = generatedKeys.getInt(1);
                        // Now insert specific WorkshopEvent details
                        String workshopQuery = "INSERT INTO WorkshopEvent (eventID, topic, duration, instructor) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement workshopStmt = connection.prepareStatement(workshopQuery)) {
                            workshopStmt.setInt(1, eventID);
                            workshopStmt.setString(2, event.getTopic());
                            workshopStmt.setDouble(3, event.getDuration());
                            workshopStmt.setString(4, event.getInstructor());
                            int workshopResult = workshopStmt.executeUpdate();
                            return workshopResult > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean saveConferenceEvent(ConferenceEvent event, int eventOrganizerID, int venueID) {
        String query = "INSERT INTO Event (eventName, eventDate, budget, status, eventType, eventOrganizerID, venueID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Insert into Event table
            preparedStatement.setString(1, event.getEventName());
            preparedStatement.setString(2, event.getEventDate());
            preparedStatement.setDouble(3, event.getBudget());
            preparedStatement.setBoolean(4, event.isActive());
            preparedStatement.setString(5, "Conference");
            preparedStatement.setInt(6, eventOrganizerID);
            preparedStatement.setInt(7, venueID);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Get the generated eventID for the new event
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int eventID = generatedKeys.getInt(1);

                        // Now insert specific ConferenceEvent details
                        String conferenceQuery = "INSERT INTO ConferenceEvent (eventID, agenda) VALUES (?, ?)";
                        try (PreparedStatement conferenceStmt = connection.prepareStatement(conferenceQuery)) {
                            conferenceStmt.setInt(1, eventID);
                            conferenceStmt.setString(2, event.getAgenda());
                            int conferenceResult = conferenceStmt.executeUpdate();
                            return conferenceResult > 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // Additional methods for handling event-related database operations can be added here
}
