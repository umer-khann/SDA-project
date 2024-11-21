package com.example.oopfiles;

import com.example.JDBC.MyJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Attendee {
    private String username;
    private String password;

    // Constructor
    public Attendee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Validate the attendee's login credentials.
     *
     * @return true if the login is successful, false otherwise
     */
    public boolean validateLogin() {
        String query = "SELECT * FROM Attendees WHERE username = ? AND password = ?";
        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.password);

            // Execute query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a matching record is found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of exceptions
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Example usage
        Attendee attendee = new Attendee("testUser", "testPassword");

        if (attendee.validateLogin()) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}
