package com.example.JDBC;

import java.sql.*;

public class TicketDBHandler {
        // Method to insert a new ticket
        public static int insertTicket(double price, int eventId, int attendeeId, int paymentId) {
            String ticketQuery = "INSERT INTO Ticket (Price, eventID, attendeeID, paymentID) VALUES (?, ?, ?, ?)";
            int ticketId = -1;

            try (Connection connection = MyJDBC.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(ticketQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

                // Set parameters for the ticket
                preparedStatement.setDouble(1, price);
                preparedStatement.setInt(2, eventId);
                preparedStatement.setInt(3, attendeeId);
                preparedStatement.setInt(4, paymentId);

                // Execute the insert
                int rowsAffected = preparedStatement.executeUpdate();

                // Retrieve the generated TicketID
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            ticketId = generatedKeys.getInt(1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ticketId; // Return the TicketID
        }

        // Method to insert a ticket purchase notification
        public static void insertTicketPurchaseNotification(int ticketId, int userId, String message, String unread) {
            String notificationQuery = "INSERT INTO TicketPurchaseNotification (TicketID, UserID, Message) VALUES (?, ?, ?)";

            try (Connection connection = MyJDBC.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(notificationQuery)) {

                // Set parameters for the notification
                preparedStatement.setInt(1, ticketId);
                preparedStatement.setInt(2, userId);
                preparedStatement.setString(3, message);

                // Execute the insert
                int rowsAffected = preparedStatement.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

