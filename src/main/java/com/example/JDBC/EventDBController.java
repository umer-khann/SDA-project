package com.example.JDBC;

import com.example.oopfiles.ConcertEvent;
import com.example.oopfiles.ConferenceEvent;
import com.example.oopfiles.Event;
import com.example.oopfiles.WorkshopEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        String query = "INSERT INTO Event (eventName, eventDate, budget, status) VALUES (?, ?, ?, ?)";

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
        String query = "UPDATE Event SET eventName = ?, eventDate = ? WHERE eventID = ?";

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

    public void showEvents(ObservableList<Event> eventList, int EventOrgID) {
        String query = "SELECT eventID, eventName, budget, eventType FROM Event WHERE eventOrganizerID = ?";
        System.out.println(EventOrgID+" in too.");
        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the EventOrgID parameter
            preparedStatement.setInt(1, EventOrgID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int eventID = resultSet.getInt("eventID");
                    String eventName = resultSet.getString("eventName");
                    float budget = resultSet.getFloat("budget");
                    String eventType = resultSet.getString("eventType");

                    Event event;
                    // Instantiate the appropriate subclass based on eventType
                    switch (eventType.toLowerCase()) {
                        case "conference":
                            event = new ConferenceEvent();
                            event.assignValues(eventID, eventName, budget, eventType);
                            break;
                        case "concert":
                            event = new ConcertEvent();
                            event.assignValues(eventID, eventName, budget, eventType);
                            break;
                        case "workshop":
                            event = new WorkshopEvent();
                            event.assignValues(eventID, eventName, budget, eventType);
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown event type: " + eventType);
                    }

                    // Add the event to the list
                    eventList.add(event);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCompleteEvents(ObservableList<Event> eventList) {
        String query = "SELECT \n" +
                "    E.eventID,\n" +
                "    E.eventName,\n" +
                "    E.eventDate,\n" +
                "    E.status,\n" +
                "    E.budget,\n" +
                "    E.eventType,\n" +
                "    WE.topic AS workshopTopic,\n" +
                "    WE.duration AS workshopDuration,\n" +
                "    WE.instructor AS workshopInstructor,\n" +
                "    CE.genre AS concertGenre,\n" +
                "    FE.performerName AS performerName,\n" +
                "    CFE.agenda AS conferenceAgenda,\n" +
                "    SE.speakerName AS speakerName\n" +
                "FROM \n" +
                "    Event E\n" +
                "LEFT JOIN \n" +
                "    WorkshopEvent WE ON E.eventID = WE.eventID\n" +
                "LEFT JOIN \n" +
                "    ConcertEvent CE ON E.eventID = CE.eventID\n" +
                "LEFT JOIN \n" +
                "    Performer FE ON CE.eventID = FE.eventID\n" +
                "LEFT JOIN \n" +
                "    ConferenceEvent CFE ON E.eventID = CFE.eventID\n" +
                "LEFT JOIN \n" +
                "    Speaker SE ON CFE.eventID = SE.eventID;\n";

        try (Connection connection = MyJDBC.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int eventID = resultSet.getInt("eventID");
                String eventName = resultSet.getString("eventName");
                String eventDate = resultSet.getString("eventDate");
                boolean status = resultSet.getBoolean("status");
                float budget = resultSet.getFloat("budget");
                String eventType = resultSet.getString("eventType");

                // Instantiate the appropriate subclass based on eventType
                Event event;
                switch (eventType.toLowerCase()) {
                    case "conference":
                        event = new ConferenceEvent();
                        event.assignAllValues(eventID, eventName, budget, eventType, eventDate, status);
                        ((ConferenceEvent) event).setAgenda(resultSet.getString("conferenceAgenda"));
                        ((ConferenceEvent) event).setSpeakerName(resultSet.getString("speakerName"));
                        break;

                    case "concert":
                        event = new ConcertEvent();
                        event.assignAllValues(eventID, eventName, budget, eventType, eventDate, status);
                        ((ConcertEvent) event).setGenre(resultSet.getString("concertGenre"));
                        ((ConcertEvent) event).setPerformerName(resultSet.getString("performerName"));
                        break;

                    case "workshop":
                        event = new WorkshopEvent();
                        event.assignAllValues(eventID, eventName, budget, eventType, eventDate, status);
                        ((WorkshopEvent) event).setTopic(resultSet.getString("workshopTopic"));
                        ((WorkshopEvent) event).setDuration(resultSet.getFloat("workshopDuration"));
                        ((WorkshopEvent) event).setInstructor(resultSet.getString("workshopInstructor"));
                        break;

                    default:
                        throw new IllegalArgumentException("Unknown event type: " + eventType);
                }

                // Add the event to the list
                eventList.add(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * Delete an event from the database by its ID.
     *
     * @param eventID The ID of the event to be deleted.
     * @return true if the deletion is successful, false otherwise.
     */
    public boolean deleteEvent(int eventID) {
        String query = "DELETE FROM Event WHERE eventID = ?";

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
        String query = "SELECT * FROM Event WHERE eventID = ?";

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
        System.out.println(eventOrganizerID+" done.");
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

    public boolean verifyEvent(int eventID) {
        // SQL query to check if the event exists in the database
        String query = "SELECT 1 FROM Event WHERE eventID = ? LIMIT 1";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the eventID parameter in the query
            preparedStatement.setInt(1, eventID);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // If a result is returned, the event exists
                return resultSet.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an exception
        }
    }

    public void updateEventBudget(int ID, double newBudget) {
        String query = "UPDATE Event SET budget = ? WHERE eventID = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameters for the query
            preparedStatement.setDouble(1, newBudget);
            preparedStatement.setInt(2, ID);

            // Execute the update query
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful
            if (rowsUpdated > 0) {
                System.out.println("Event budget updated successfully for Event ID: " + ID);
            } else {
                System.out.println("Event ID not found. No update performed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error updating event budget for Event ID: " + ID);
        }
    }



    // Additional methods for handling event-related database operations can be added here
}
