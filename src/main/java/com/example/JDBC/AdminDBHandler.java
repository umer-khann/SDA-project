package com.example.JDBC;

import com.example.oopfiles.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDBHandler {
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
    public List<Notification> retrieveNotifications() {
        String query = "SELECT UserID, UserType, GeneralNotificationID, Message, NotificationType, CreatedAt, EventID " +
                "FROM generalnotification";
        List<Notification> notifications = new ArrayList<>();

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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
