package com.example.JDBC;

import com.example.JDBC.MyJDBC;
import com.example.oopfiles.EventOrganizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventOrganizerDBHandler {

    /**
     * Validate the login credentials for an event organizer.
     *
     * @param username The username of the event organizer.
     * @param password The password of the event organizer.
     * @return true if the login is successful, false otherwise.
     */
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

    /**
     * Sign up a new event organizer.
     *
     * @param organizer The EventOrganizer object containing organizer details.
     */
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

    /**
     * Assign an EventOrganizer object with details from the database based on username and password.
     *
     * @param organizer The EventOrganizer object to populate.
     * @param uname The username of the event organizer.
     * @param pass The password of the event organizer.
     */
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

    /**
     * Check if a username already exists in the EventOrganizers table.
     *
     * @param username The username to check.
     * @return true if the username exists, false otherwise.
     */
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




    /**
     * Check if an email already exists in the EventOrganizers table.
     *
     * @param email The email to check.
     * @return true if the email exists, false otherwise.
     */
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
}
