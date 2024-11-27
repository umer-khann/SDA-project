package com.example.JDBC;

import java.sql.*;


public class PaymentDBHandler {
    private static PaymentDBHandler instance;
    public static synchronized PaymentDBHandler getInstance() {
        if (instance == null) {
            instance = new PaymentDBHandler();
        }
        return instance;
    }

        // Method to insert a new payment
        public static int insertPayment(double amount, String status, int transactionId, int userId, Integer eventId) {
            String paymentQuery = "INSERT INTO Payment (Amount, Status, TransactionID, UserID, EventID) VALUES (?, ?, ?, ?, ?)";
            int paymentId = -1;

            try (Connection connection = MyJDBC.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(paymentQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

                // Set parameters for the payment
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, status);
                preparedStatement.setInt(3, transactionId);
                preparedStatement.setInt(4, userId);

                // Handle nullable fields
                if (eventId != null) {
                    preparedStatement.setInt(5, eventId);
                } else {
                    preparedStatement.setNull(5, java.sql.Types.INTEGER);
                }


                // Execute the insert
                int rowsAffected = preparedStatement.executeUpdate();

                // Retrieve the generated PaymentID
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            paymentId = generatedKeys.getInt(1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return paymentId; // Return the PaymentID
        }

        // Method to insert a payment notification
        public static boolean insertPaymentNotification(int paymentId, int userId, String message, double amount) {
            String notificationQuery = "INSERT INTO PaymentNotification (PaymentID, UserID, Message, amount) VALUES (?, ?, ?, ?)";

            try (Connection connection = MyJDBC.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(notificationQuery)) {

                // Set parameters for the notification
                preparedStatement.setInt(1, paymentId);
                preparedStatement.setInt(2, userId);
                preparedStatement.setString(3, message);
                preparedStatement.setString(4, String.valueOf(amount));


                // Execute the insert
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0; // Return true if the insert was successful

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }



    public static void insertEventRegistrationNotification(int eventId, int userId, String message, String status) {
        String query = "INSERT INTO EventRegistrationNotification (EventID, UserID, Message, Status) VALUES (?, ?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, eventId);
            statement.setInt(2, userId);
            statement.setString(3, message);
            statement.setString(4, status);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}