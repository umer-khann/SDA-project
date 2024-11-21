package com.example.oopfiles;

import com.example.JDBC.AttendeeDBController;

public class Attendee {
    private String username;
    private String password;
    private AttendeeDBController dbController;

    // Constructor
    public Attendee(String username, String password) {
        this.username = username;
        this.password = password;
        dbController = new AttendeeDBController();
    }

    // Method to validate login
    public boolean validateLogin() {
        // Call the AttendeeDBController to validate login credentials
        return dbController.validateLogin(this.username, this.password);
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
