package com.example.JDBC;

import com.example.oopfiles.*;
import javafx.collections.FXCollections;
import com.example.oopfiles.*;
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


    public void showEvents(ObservableList<Event> eventList, int EventOrgID) {
        String query = "SELECT eventID, eventName, budget, eventType FROM Event WHERE eventOrganizerID = ?";
        System.out.println(EventOrgID + " in too.");
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

    public void showEventResources(ObservableList<Event> eventList, int EventOrgID) {
        // Modify the query to also select eventName along with staff, seats, and equipment
        String query = "SELECT e.eventID, e.eventName, e.staff, e.seats, e.technicalEquipmentQuantity " +
                "FROM Event e " +
                "WHERE e.eventOrganizerID = ?";
        System.out.println(EventOrgID + " in too.");

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the EventOrgID parameter
            preparedStatement.setInt(1, EventOrgID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int eventID = resultSet.getInt("eventID");
                    String eventName = resultSet.getString("eventName");
                    int staff = resultSet.getInt("staff");
                    int seats = resultSet.getInt("seats");
                    int equipment = resultSet.getInt("technicalEquipmentQuantity");

                    Event event = new Event() {
                        @Override
                        public boolean createEvent() {
                            return false;
                        }
                    };

                    // Find the Event object associated with the eventID
                    // Event event = findEventById(eventList, eventID);
                    // if (event != null) {
                    // Set values for eventName, staff, seats, and equipment
                    event.setEventID(eventID);
                    event.setEventName(eventName);
                    event.setStaff(staff);
                    event.setSeats(seats);
                    event.setNoOfTechnicalEquipment(equipment);
                    //  } else {
                    //      System.out.println("Event not found for eventID: " + eventID);
                    //   }


                    eventList.add(event);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Helper method to find an Event by eventID from the eventList
    private Event findEventById(ObservableList<Event> eventList, int eventID) {
        for (Event event : eventList) {
            if (event.getEventID() == eventID) {
                return event;
            }
        }
        return null;
    }


    public void showCompleteEvents(ObservableList<Event> eventList, int EventOrgID) {
        // Modify the query to filter by EventOrgID
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
                "    Speaker SE ON CFE.eventID = SE.eventID\n" +
                "WHERE E.eventOrganizerID = ?";  // Add filter for the event organizer ID


        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the event organizer ID in the query
            preparedStatement.setInt(1, EventOrgID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEventsForAttendee(ObservableList<Event> eventList) {
        // Modify the query to include venue location
        String query = "SELECT \n" +
                "    E.eventID,\n" +
                "    E.eventName,\n" +
                "    E.eventDate,\n" +
                "    E.status,\n" +
                "    E.budget,\n" +
                "    E.eventType,\n" +
                "    VE.venueName,\n" +  // Add venueName column
                "    VE.venueType,\n" +  // Add venueType column
                "    VE.location,\n" +   // Add location column
                "    VE.capacity,\n" +   // Explicitly select the capacity column
                "    WE.topic AS workshopTopic,\n" +
                "    WE.duration AS workshopDuration,\n" +
                "    WE.instructor AS workshopInstructor,\n" +
                "    CE.genre AS concertGenre,\n" +
                "    FE.performerName AS performerName,\n" +
                "    CFE.agenda AS conferenceAgenda,\n" +
                "    SE.speakerName AS speakerName,\n" +
                "    IV.roomNumber AS indoorRoomNumber,\n" +  // Indoor specific column
                "    IV.floor AS indoorFloor,\n" +  // Indoor specific column
                "    OV.weatherPreparedness AS outdoorWeather,\n" +  // Outdoor specific column
                "    OV.additionalCapacity AS outdoorCapacity\n" +  // Outdoor specific column
                "FROM \n" +
                "    Event E\n" +
                "LEFT JOIN \n" +
                "    Venue VE ON E.venueID = VE.venueID\n" +  // Join with Venue
                "LEFT JOIN \n" +
                "    WorkshopEvent WE ON E.eventID = WE.eventID\n" +
                "LEFT JOIN \n" +
                "    ConcertEvent CE ON E.eventID = CE.eventID\n" +
                "LEFT JOIN \n" +
                "    Performer FE ON CE.eventID = FE.eventID\n" +
                "LEFT JOIN \n" +
                "    ConferenceEvent CFE ON E.eventID = CFE.eventID\n" +
                "LEFT JOIN \n" +
                "    Speaker SE ON CFE.eventID = SE.eventID\n" +
                "LEFT JOIN \n" +
                "    IndoorVenue IV ON VE.venueID = IV.indoorVenueID\n" +  // Indoor venue join
                "LEFT JOIN \n" +
                "    OutdoorVenue OV ON VE.venueID = OV.outdoorVenueID";  // Outdoor venue join


        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int eventID = resultSet.getInt("eventID");
                    String eventName = resultSet.getString("eventName");
                    String eventDate = resultSet.getString("eventDate");
                    boolean status = resultSet.getBoolean("status");
                    float budget = resultSet.getFloat("budget");
                    String eventType = resultSet.getString("eventType");
                    String venueName = resultSet.getString("venueName");
                    String venueType = resultSet.getString("venueType");
                    String location = resultSet.getString("location");  // Fetch the location

                    // Instantiate the appropriate event based on eventType
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

                    // Create the appropriate venue based on venueType
                    Venue venue;
                    if ("Indoor".equalsIgnoreCase(venueType)) {
                        venue = new IndoorVenue(venueName, location, resultSet.getInt("capacity"),
                                resultSet.getString("indoorRoomNumber"), resultSet.getInt("indoorFloor"));
                    } else if ("Outdoor".equalsIgnoreCase(venueType)) {
                        venue = new OutdoorVenue(venueName, location, resultSet.getInt("capacity"),
                                resultSet.getString("outdoorWeather"), resultSet.getInt("outdoorCapacity"));
                    } else {
                        throw new IllegalArgumentException("Unknown venue type: " + venueType);
                    }

                    // Set the venue to the event
                    event.setVenue(venue);

                    // Add the event to the list
                    eventList.add(event);
                }
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
        System.out.println(eventOrganizerID + " done.");
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

    public boolean updateEvent(int eventID, String newName, Date newDate) {
        // Construct the SQL query dynamically based on which fields are provided
        StringBuilder query = new StringBuilder("UPDATE Event SET ");
        boolean hasName = newName != null && !newName.isEmpty();
        boolean hasDate = newDate != null;

        if (hasName) {
            query.append("eventName = ?");
        }

        if (hasDate) {
            if (hasName) {
                query.append(", ");
            }
            query.append("eventDate = ?");
        }

        query.append(" WHERE eventID = ?");

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            int index = 1;

            if (hasName) {
                preparedStatement.setString(index++, newName);
            }

            if (hasDate) {
                preparedStatement.setDate(index++, new java.sql.Date(newDate.getTime()));
            }

            preparedStatement.setInt(index, eventID);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Return true if at least one row is affected
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertEventUpdateNotification(int eventID, int userID, String message) {
        String sql = "INSERT INTO EventUpdateNotification (EventID, UserID, Message) VALUES (?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, eventID);
            pstmt.setInt(2, userID);
            pstmt.setString(3, message);

            pstmt.executeUpdate();  // Execute the insert

        } catch (SQLException e) {
            System.out.println("Error inserting notification: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void retrieveAttendees(ObservableList<Attendee> attendeeList, int eventID) {
        // Updated query to join Ticket and Attendees tables based on eventID
        String query = "SELECT a.attendeeID, a.name, a.email, a.contactDetails, a.loyaltyPoints, a.type " +
                "FROM Attendees a " +
                "INNER JOIN Ticket t ON a.attendeeID = t.attendeeID " +
                "WHERE t.eventID = ?"; // Matching the eventID from the Ticket table

        System.out.println(eventID + " in retrieveAttendees.");

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the eventID parameter to filter by the event
            preparedStatement.setInt(1, eventID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Clear the list before populating it to avoid duplicates if the method is called multiple times
                attendeeList.clear();

                while (resultSet.next()) {
                    int attendeeID = resultSet.getInt("attendeeID");
                    String attendeeName = resultSet.getString("name");
                    String attendeeEmail = resultSet.getString("email");
                    String attendeeContact = resultSet.getString("contactDetails");
                    int loyaltyPoints = resultSet.getInt("loyaltyPoints");
                    String attendeeType = resultSet.getString("type");  // Column 'type' in the database

                    User attendee;

                    // Instantiate the appropriate subclass based on the type
                    if ("VIP".equalsIgnoreCase(attendeeType)) {
                        attendee = new VipAttendee();  // Instantiate the concrete class for VIP
                        attendee.setUserID(attendeeID);
                        attendee.setName(attendeeName);
                        attendee.setEmail(attendeeEmail);
                        attendee.setContactDetails(attendeeContact);
                        attendee.setLoyaltyPoints(loyaltyPoints);
                    } else {
                        // Default to RegularAttendee or another subclass for General
                        attendee = new GeneralAttendee();  // Instantiate the concrete class for General
                        attendee.setUserID(attendeeID);
                        attendee.setName(attendeeName);
                        attendee.setEmail(attendeeEmail);  // Corrected to set the email
                        attendee.setContactDetails(attendeeContact);
                        attendee.setLoyaltyPoints(loyaltyPoints);
                    }

                    // Add the attendee to the list, cast to Attendee if needed
                    attendeeList.add((Attendee) attendee);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEventResources(int eventID, Integer staff, Integer seats, Integer equipment) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE EventResources SET ");
        boolean hasUpdates = false;

        // Append columns that need to be updated
        if (staff != null) {
            queryBuilder.append("staff = ?, ");
            hasUpdates = true;
        }
        if (seats != null) {
            queryBuilder.append("seats = ?, ");
            hasUpdates = true;
        }
        if (equipment != null) {
            queryBuilder.append("equipment = ?, ");
            hasUpdates = true;
        }

        // Remove the trailing comma and space if there are updates
        if (hasUpdates) {
            queryBuilder.setLength(queryBuilder.length() - 2);
            queryBuilder.append(" WHERE eventID = ?");
        } else {
            System.out.println("No changes to update.");
            return;
        }

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {

            int parameterIndex = 1;

            // Set the values for the prepared statement dynamically
            if (staff != null) {
                preparedStatement.setInt(parameterIndex++, staff);
            }
            if (seats != null) {
                preparedStatement.setInt(parameterIndex++, seats);
            }
            if (equipment != null) {
                preparedStatement.setInt(parameterIndex++, equipment);
            }
            preparedStatement.setInt(parameterIndex, eventID);

            // Execute the update query
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Event resources updated in the database.");
            } else {
                System.out.println("No event found with eventID: " + eventID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}