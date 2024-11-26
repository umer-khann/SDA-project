package com.example.JDBC;

import com.example.JDBC.MyJDBC;
import com.example.oopfiles.EventOrganizer;
import com.example.oopfiles.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventOrganizerDBHandler {


    public boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM EventOrganizers WHERE username = ? AND password = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a matching record is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of exceptions
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void signUpEventOrganizer(EventOrganizer organizer) {
        String query = "INSERT INTO EventOrganizers (name, email, contactDetails, experience, username, password) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, organizer.getName());
            preparedStatement.setString(2, organizer.getEmail());
            preparedStatement.setString(3, organizer.getContactDetails());
            preparedStatement.setInt(4, organizer.getExperienceLevel());
            preparedStatement.setString(5, organizer.getUserName());
            preparedStatement.setString(6, organizer.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event organizer registered successfully!");
            } else {
                System.out.println("Failed to register the event organizer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void assignEventOrganizer(EventOrganizer organizer, String uname, String pass) {
        String query = "SELECT * FROM EventOrganizers WHERE username = ? AND password = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, pass);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    organizer.setUserID(resultSet.getInt("eventOrganizerID"));
                    organizer.setName(resultSet.getString("name"));
                    organizer.setEmail(resultSet.getString("email"));
                    organizer.setContactDetails(resultSet.getString("contactDetails"));
                    organizer.setExperienceLevel(resultSet.getInt("experience"));
                    organizer.setUserName(resultSet.getString("username"));
                    organizer.setPassword(resultSet.getString("password"));
                } else {
                    System.out.println("No event organizer found with the given credentials.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean usernameExists(String username) {
        String query = "SELECT * FROM EventOrganizers WHERE username = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a matching username is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getEventOrganizerID(String username) {
        String query = "SELECT eventOrganizerID FROM EventOrganizers WHERE username = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("eventOrganizerID"); // Return the ID if found
                } else {
                    System.out.println("No event organizer found with the given username.");
                    return -1; // Return -1 if no matching record is found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return -1 in case of any error
        }
    }

    public void addNotification(int userID, int userType, String message, String notificationType) {
        String query = "INSERT INTO GeneralNotification (UserID, UserType, Message, NotificationType, CreatedAt) " +
                "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the query
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userType);  // Send '2' for Event Organizer
            preparedStatement.setString(3, message);
            preparedStatement.setString(4, notificationType);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Notification added successfully!");
            } else {
                System.out.println("Failed to add notification.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public boolean emailExists(String email) {
        String query = "SELECT * FROM EventOrganizers WHERE email = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a matching email is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean RemoveEventOrganizer(int organizerId, Integer newOrganizerId) {
        String checkManagedEventsQuery = "SELECT COUNT(*) FROM Event WHERE eventOrganizerID = ?";
        String updateEventsQuery = "UPDATE Event SET eventOrganizerID = ? WHERE eventOrganizerID = ?";
        String deleteOrganizerQuery = "DELETE FROM eventorganizers WHERE eventOrganizerID = ?";

        try (Connection connection = MyJDBC.getConnection()) {
            connection.setAutoCommit(false); // Enable transaction management

            // Check if the organizer manages any events
            try (PreparedStatement checkEventsStmt = connection.prepareStatement(checkManagedEventsQuery)) {
                checkEventsStmt.setInt(1, organizerId);
                ResultSet eventCountResult = checkEventsStmt.executeQuery();
                eventCountResult.next();
                int eventCount = eventCountResult.getInt(1);

                if (eventCount > 0) {
                    // Organizer manages events; ensure a new organizer ID is provided
                    if (newOrganizerId == null) {
                        System.out.println("Organizer with ID " + organizerId + " manages events. A new organizer ID must be provided.");
                        connection.rollback();
                        return false;
                    }

                    // Reassign events to the new organizer
                    try (PreparedStatement updateEventsStmt = connection.prepareStatement(updateEventsQuery)) {
                        updateEventsStmt.setInt(1, newOrganizerId);
                        updateEventsStmt.setInt(2, organizerId);
                        int updatedRows = updateEventsStmt.executeUpdate();
                        System.out.println("Reassigned " + updatedRows + " events to organizer with ID " + newOrganizerId);
                    }
                }

                // Delete the organizer
                try (PreparedStatement deleteOrganizerStmt = connection.prepareStatement(deleteOrganizerQuery)) {
                    deleteOrganizerStmt.setInt(1, organizerId);
                    int deletedRows = deleteOrganizerStmt.executeUpdate();
                    if (deletedRows > 0) {
                        System.out.println("Organizer with ID " + organizerId + " has been removed.");
                        connection.commit();
                        return true;
                    } else {
                        System.out.println("Failed to remove the organizer.");
                        connection.rollback();
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    public void showEvents(ObservableList<EventOrganizer> eventList) {
        String query = "SELECT eo.eventOrganizerID, eo.name AS organizerName, e.eventID, e.eventName " +
                "FROM EventOrganizers eo " +
                "LEFT JOIN Event e ON eo.eventOrganizerID = e.eventOrganizerID " +
                "ORDER BY eo.eventOrganizerID, e.eventID";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // While iterating through the result set
            while (resultSet.next()) {
                int organizerID = resultSet.getInt("eventOrganizerID");
                String organizerName = resultSet.getString("organizerName");
                Integer eventID = resultSet.getInt("eventID");
                if (resultSet.wasNull()) eventID = null; // Handle null values
                String eventName = resultSet.getString("eventName");

                // Debugging: Check values for each row
                System.out.println("Processing: Organizer ID = " + organizerID + ", Event ID = " + eventID);

                // Create a new EventOrganizer object for each record (new organizer + event combination)
                EventOrganizer currentOrganizer = new EventOrganizer();
                currentOrganizer.setid(organizerID);  // Set the organizer ID
                currentOrganizer.setName(organizerName != null ? organizerName : "Default Name");
                System.out.println("New Organizer Created: ID = " + organizerID + ", Name = " + organizerName);

                // Add event details to the current organizer if eventID is not null
                if (eventID != null) {
                    currentOrganizer.addEventDetails(eventID, eventName != null ? eventName : "Default Event");
                    // Debugging: Print added event details
                    System.out.println("Added Event: ID = " + eventID + ", Name = " + eventName);
                }

                // Add the current organizer (with the event) to the list
                eventList.add(currentOrganizer);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Notification> recieveNotifications(int ID) {
        String query = "SELECT UserID, UserType, GeneralNotificationID, Message, NotificationType, CreatedAt, EventID " +
                "FROM generalnotification WHERE UserType = 2 and UserID = ? ";
        List<Notification> notifications = new ArrayList<>();

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ID);
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int notificationID = resultSet.getInt("GeneralNotificationID");
                String message = resultSet.getString("Message");
                String notificationType = resultSet.getString("NotificationType");
                String createdAt = resultSet.getString("CreatedAt");
                int eventID = resultSet.getInt("EventID");
                int userID = resultSet.getInt("UserID");
                int ut = resultSet.getInt("UserType");
                String userType;
                if(ut == 1){
                    userType = "Admin";
                }
                else if(ut == 2){
                    userType = "Event Organizer";
                }
                else{
                    userType = "Attendee";
                }
                // Add each notification to the list
                notifications.add(new Notification(notificationID, userID, userType, message, notificationType, createdAt, eventID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;
    }
}
