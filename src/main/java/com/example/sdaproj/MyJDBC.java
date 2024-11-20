package com.example.sdaproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC {
    public static void main(String[] args) {
        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/sdaproj", // Database URL
                    "umer", // Username
                    "umer"  // Password
            );

            // Create a statement object
            Statement statement = connection.createStatement();

            // Execute SELECT query
            ResultSet resultSet = statement.executeQuery(
                    "SELECT A.*, V.VIPLevel, V.accessToExclusiveAreas " +
                            "FROM Attendees A " +
                            "JOIN VIPAttendees V ON A.attendeeID = V.attendeeID;"
            );

            // Display the results
            System.out.printf("%-5s %-15s %-25s %-15s %-10s %-5s %-10s %-50s%n",
                    "ID", "Name", "Email", "Contact", "Points", "Ticket", "VIPLevel", "ExclusiveAreas");
            System.out.println("-----------------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                int attendeeID = resultSet.getInt("attendeeID");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String contactDetails = resultSet.getString("contactDetails");
                int loyaltyPoints = resultSet.getInt("loyaltyPoints");
                int ticketID = resultSet.getInt("ticketID");
                String vipLevel = resultSet.getString("VIPLevel");
                String accessToExclusiveAreas = resultSet.getString("accessToExclusiveAreas");

                System.out.printf("%-5d %-15s %-25s %-15s %-10d %-5d %-10s %-50s%n",
                        attendeeID, name, email, contactDetails, loyaltyPoints, ticketID, vipLevel, accessToExclusiveAreas);
            }

            // Execute UPDATE query to modify loyalty points for a specific attendee
            int updatedRows = statement.executeUpdate(
                    "UPDATE Attendees " +
                            "SET loyaltyPoints = loyaltyPoints + 10 " +
                            "WHERE attendeeID = 1;"
            );

            if (updatedRows > 0) {
                System.out.println("\nLoyalty points updated successfully for attendee ID 1.");
            } else {
                System.out.println("\nNo rows were updated. Please check the attendee ID.");
            }

            // Re-execute SELECT query to verify changes
            resultSet = statement.executeQuery(
                    "SELECT attendeeID, name, loyaltyPoints FROM Attendees WHERE attendeeID = 1;"
            );

            System.out.println("\nUpdated Record:");
            System.out.printf("%-5s %-15s %-10s%n", "ID", "Name", "Points");
            System.out.println("-----------------------------------");

            while (resultSet.next()) {
                int attendeeID = resultSet.getInt("attendeeID");
                String name = resultSet.getString("name");
                int loyaltyPoints = resultSet.getInt("loyaltyPoints");

                System.out.printf("%-5d %-15s %-10d%n", attendeeID, name, loyaltyPoints);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}
