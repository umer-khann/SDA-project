package com.example.JDBC;

import com.example.JDBC.MyJDBC;
import com.example.oopfiles.Attendee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendeeDBController {

    /**
     * Validate the login credentials for an attendee.
     *
     * @param username The username of the attendee.
     * @param password The password of the attendee.
     * @return true if the login is successful, false otherwise.
     */
    public boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM Attendees WHERE username = ? AND password = ?";

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
    public void signUpAttendee(Attendee attendee) {
        String query = "INSERT INTO Attendees (name, email, contactDetails, username, password, loyaltyPoints) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, attendee.getName());
            preparedStatement.setString(2, attendee.getEmail());
            preparedStatement.setString(3, attendee.getContactDetails());
            preparedStatement.setString(4, attendee.getUserName());
            preparedStatement.setString(5, attendee.getPassword());
            preparedStatement.setInt(6, 10); // default 0 for new attendees

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Attendee registered successfully!");
            } else {
                System.out.println("Failed to register the attendee.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void AssignAttendee(Attendee attendee, String uname, String pass) {
        String query = "SELECT * FROM Attendees WHERE username = ? AND password = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, pass);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Move the cursor to the first row
                if (resultSet.next()) {
                    attendee.setUserID(resultSet.getInt("attendeeID"));
                    attendee.setName(resultSet.getString("name"));
                    attendee.setEmail(resultSet.getString("email"));
                    attendee.setContactDetails(resultSet.getString("contactDetails"));
                    attendee.setLoyaltyPoints(resultSet.getInt("loyaltyPoints"));
                    attendee.setUserName(resultSet.getString("username"));
                    attendee.setPassword(resultSet.getString("password"));
                } else {
                    // Handle case when no matching user is found
                    System.out.println("No attendee found with the given credentials.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean usernameExists(String username) {
        String query = "SELECT * FROM Attendees WHERE username = ?";

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

    // Method to check if the email exists in the database
    public boolean emailExists(String email) {
        String query = "SELECT * FROM Attendees WHERE email = ?";

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

    // Additional methods for Attendee-related queries can go here, e.g., registration, fetching attendee data, etc.
}
