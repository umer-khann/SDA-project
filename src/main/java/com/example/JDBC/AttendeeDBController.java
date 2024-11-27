package com.example.JDBC;

import com.example.JDBC.MyJDBC;
import com.example.oopfiles.Attendee;
import com.example.oopfiles.GeneralAttendee;
import com.example.oopfiles.VipAttendee;
import com.example.oopfiles.*;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendeeDBController {
    private static AttendeeDBController instance;
    public static synchronized AttendeeDBController getInstance() {
        if (instance == null) {
            instance = new AttendeeDBController();
        }
        return instance;
    }
    /**
     * Validate the login credentials for an attendee.
     *
     * @param username The username of the attendee.
     * @param password The password of the attendee.
     * @return true if the login is successful, false otherwise.
     */
    public boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM Attendees WHERE username = ? AND password = ?";
        if(retrievePassword(username).equals(password)){
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
        return false;
    }
    public String retrievePassword(String username) {
        String query = "SELECT password FROM attendees WHERE username = ?";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password"); // Return the password if found
                } else {
                    return "Username not found"; // Return message if username doesn't exist
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving password"; // Handle exception case
        }
    }
    public boolean signUpAttendee(Attendee attendee,String add) {
        if(attendee.getType().equalsIgnoreCase("general") && !isValidMembershipLevel(add)){
            showErrorAlert("Invalid membership level choose from Basic, Silver, Gold, Platinum");
            return false;}
        String query = "INSERT INTO Attendees (name, email, contactDetails, username, password, loyaltyPoints, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Insert into the main Attendees table
            preparedStatement.setString(1, attendee.getName());
            preparedStatement.setString(2, attendee.getEmail());
            preparedStatement.setString(3, attendee.getContactDetails());
            preparedStatement.setString(4, attendee.getUserName());
            preparedStatement.setString(5, attendee.getPassword());
            preparedStatement.setInt(6, 10); // Initial loyalty points for a new attendee
            preparedStatement.setString(7, attendee.gettype()); // Type: General or VIP

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Attendee registered successfully!");

                // Retrieve the newly created attendee's ID
                int attendeeID = getID(attendee.getUserName());
                attendee.setUserID(attendeeID);

                // Insert additional data based on attendee type
                if ("General".equalsIgnoreCase(attendee.gettype())) {
                    // If the attendee is of type General, insert into generalattendees table
                    insertGeneralAttendee(attendeeID,add);
                } else if ("VIP".equalsIgnoreCase(attendee.gettype())) {
                    // If the attendee is of type VIP, insert into vipattendees table
                    insertVIPAttendee(attendeeID,add);
                }

                return true;
            } else {
                System.out.println("Failed to register the attendee.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // Helper method to insert General Attendee
    private void insertGeneralAttendee(int attendeeID, String membershipLevel) {
        // Ensure that the membershipLevel is valid
        if (!isValidMembershipLevel(membershipLevel)) {
            showErrorAlert("Invalid membership level: " + membershipLevel);
            return;
        }

        String query = "INSERT INTO generalattendees (attendeeID, membershipLevel) VALUES (?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, attendeeID);
            preparedStatement.setString(2, membershipLevel); // Set the validated membership level
            preparedStatement.executeUpdate();

            System.out.println("General attendee registered successfully with " + membershipLevel + " membership.");
        } catch (Exception e) {
            // Show the SQL error in an alert
            showErrorAlert("Error inserting general attendee: " + e.getMessage());
        }
    }

    // Helper method to validate membership level
    private boolean isValidMembershipLevel(String membershipLevel) {
        return "Basic".equalsIgnoreCase(membershipLevel) ||
                "Silver".equalsIgnoreCase(membershipLevel) ||
                "Gold".equalsIgnoreCase(membershipLevel) ||
                "Platinum".equalsIgnoreCase(membershipLevel);
    }

    // Method to show error alert
    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    // Helper method to insert VIP Attendee with specified VIP level and access area
    private void insertVIPAttendee(int attendeeID, String VIPLevel) throws SQLException {
        String query = "INSERT INTO vipattendees (attendeeID, VIPLevel, accessToExclusiveAreas) VALUES (?, ?, ?)";

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, attendeeID);
            preparedStatement.setString(2, VIPLevel); // Set the VIP level based on the input
            preparedStatement.setString(3, "BackStage pass, Access to VIP Lounge, Front-row seating"); // Set access information based on the input
            preparedStatement.executeUpdate();

            System.out.println("VIP attendee registered successfully with " + VIPLevel + " VIP level and access to ");
        } catch (Exception e) {
            throw new RuntimeException("Error inserting VIP attendee: " + e.getMessage(), e);
        }
    }


    public int getID(String username) {
        String query = "select attendeeID from attendees where username = ? ";
        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Move the cursor to the first row
                if (resultSet.next()) {
                    return (resultSet.getInt("attendeeID"));
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
        return 0;
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

    public static boolean removeAttendee(int attendeeID) {
        String query = "DELETE FROM Attendees WHERE attendeeID = ?";
        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, attendeeID);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was affected
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return false; // Return false in case of an error
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


    public boolean updateAttendee(int attendeeID, String name, String email, String contact, Integer loyaltyPoints) {
        StringBuilder query = new StringBuilder("UPDATE Attendees SET ");
        boolean hasFieldsToUpdate = false;

        // Dynamically build the query based on non-null parameters
        if (name != null) {
            query.append("name = ?, ");
            hasFieldsToUpdate = true;
        }
        if (email != null) {
            query.append("email = ?, ");
            hasFieldsToUpdate = true;
        }
        if (contact != null) {
            query.append("contactDetails = ?, ");
            hasFieldsToUpdate = true;
        }
        if (loyaltyPoints != null) {
            query.append("loyaltyPoints = ?, ");
            hasFieldsToUpdate = true;
        }

        if (!hasFieldsToUpdate) {
            // No fields to update
            return false;
        }

        // Remove trailing comma and space, add WHERE clause
        query.setLength(query.length() - 2);
        query.append(" WHERE attendeeID = ?");

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            // Set parameters dynamically
            int index = 1;
            if (name != null) preparedStatement.setString(index++, name);
            if (email != null) preparedStatement.setString(index++, email);
            if (contact != null) preparedStatement.setString(index++, contact);
            if (loyaltyPoints != null) preparedStatement.setInt(index++, loyaltyPoints);

            // Set the attendee ID
            preparedStatement.setInt(index, attendeeID);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Attendee retrieveAttendee(int attendeeID) {
        Attendee attendee = null;

        // SQL query to fetch the attendee details
        String query = "SELECT a.attendeeID, a.name, a.email, a.contactDetails, a.loyaltyPoints, a.username, a.password, a.type, " +
                "ga.membershipLevel, va.VIPLevel, va.accessToExclusiveAreas " +
                "FROM attendees a " +
                "LEFT JOIN generalattendees ga ON a.attendeeID = ga.attendeeID " +
                "LEFT JOIN vipattendees va ON a.attendeeID = va.attendeeID " +
                "WHERE a.attendeeID = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, attendeeID);  // Set the attendeeID parameter

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Fetch common attendee details
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String contactDetails = rs.getString("contactDetails");
                    int loyaltyPoints = rs.getInt("loyaltyPoints");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String type = rs.getString("type");

                    // Instantiate the correct type of attendee based on the type field
                    if ("VIP".equalsIgnoreCase(type)) {
                        // VIP attendee - populate specific fields
                        VipAttendee vipAttendee = new VipAttendee();
                        vipAttendee.setUserID(attendeeID);
                        vipAttendee.setName(name);
                        vipAttendee.setEmail(email);
                        vipAttendee.setContactDetails(contactDetails);
                        vipAttendee.setLoyaltyPoints(loyaltyPoints);
                        vipAttendee.setUserName(username);
                        vipAttendee.setPassword(password);
                        vipAttendee.setVIPLevel(rs.getString("VIPLevel"));
                        vipAttendee.setAccessToExclusiveAreas(rs.getString("accessToExclusiveAreas"));
                        attendee = vipAttendee;
                    } else {
                        // General attendee - populate specific fields
                        GeneralAttendee generalAttendee = new GeneralAttendee();
                        generalAttendee.setUserID(attendeeID);
                        generalAttendee.setName(name);
                        generalAttendee.setEmail(email);
                        generalAttendee.setContactDetails(contactDetails);
                        generalAttendee.setLoyaltyPoints(loyaltyPoints);
                        generalAttendee.setUserName(username);
                        generalAttendee.setPassword(password);
                        generalAttendee.setMembershipLevel(rs.getString("membershipLevel"));
                        attendee = generalAttendee;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle any SQL exceptions
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return attendee;
    }


    public List<EventUpdateNotification> retrieveEventUp(int attendeeID) {
        String query = "SELECT * FROM EventUpdateNotification WHERE UserID= ?";
        List<EventUpdateNotification> notifications = new ArrayList<>();

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, attendeeID);  // Set the attendeeID parameter

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int eventUpdateNotificationID = resultSet.getInt("eventUpdateNotificationID");
                int eventID = resultSet.getInt("eventID");
                int userID = resultSet.getInt("userID");
                String message = resultSet.getString("message");
                String status = resultSet.getString("status");
                String createdAt = resultSet.getString("createdAt");
                String userType;
                notifications.add(new EventUpdateNotification(eventUpdateNotificationID, eventID, userID, message, status, createdAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;

    }

    public List<EventRegistrationNotification> AttendeeReg(int attendeeID) {
        String query = "SELECT * FROM EventRegistrationNotification WHERE UserID = ?";
        List<EventRegistrationNotification> notifications = new ArrayList<>();

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, attendeeID);  // Set the attendeeID parameter

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();


            // Process the result set
            while (resultSet.next()) {
                int notificationID = resultSet.getInt("EventRegistrationNotificationID");
                int eventID = resultSet.getInt("EventID");
                int userID = resultSet.getInt("UserID");
                String message = resultSet.getString("Message");
                String status = resultSet.getString("Status");
                String createdAt = resultSet.getString("CreatedAt");

                // Add each notification to the list
                notifications.add(new EventRegistrationNotification(notificationID, eventID, userID, message, status, createdAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;

    }

    public List<TicketPurchaseNotification> GetTicketNotif(int attendeeID) {
        String query = "SELECT * from TicketPurchaseNotification WHERE UserID = ?";
        List<TicketPurchaseNotification> notifications = new ArrayList<>();

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, attendeeID);  // Set the attendeeID parameter
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int notificationID = resultSet.getInt("TicketPurchaseNotificationID");
                int userID = resultSet.getInt("UserID");
                String message = resultSet.getString("Message");
                String notificationType = resultSet.getString("status");
                String createdAt = resultSet.getString("CreatedAt");
                int ticketID = resultSet.getInt("TicketID");

                // Create a TicketPurchaseNotification object using retrieved data
                notifications.add(new TicketPurchaseNotification(notificationID, ticketID, userID, message, notificationType, createdAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public List<PaymentNotification> GetPaymentNotif(int attendeeID) {
        String query = "SELECT * from PaymentNotification WHERE UserID = ?";
        List<PaymentNotification> notifications = new ArrayList<>();

        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, attendeeID);  // Set the attendeeID parameter

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int notificationID = resultSet.getInt("PaymentNotificationID");
                int userID = resultSet.getInt("UserID");
                String message = resultSet.getString("Message");
                String notificationType = resultSet.getString("status");
                String createdAt = resultSet.getString("CreatedAt");
                int paymentID = resultSet.getInt("PaymentID");

                // Create a PaymentNotification object using retrieved data
                notifications.add(new PaymentNotification(notificationID, paymentID, userID, message, notificationType, createdAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;
    }
}
