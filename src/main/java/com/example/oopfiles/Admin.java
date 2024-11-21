package com.example.oopfiles;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private List<String> assignedEvents;
    private String role;

    // Constructor
    public Admin() {
        super();
    }
    @Override
    public boolean login(String uname, String pass) {
        // Implementation for Event Organizer login
        this.loggedIn = true;
        System.out.println("Event Organizer " + name + " has logged in.");
        return loggedIn;
    }

    @Override
    public boolean logout() {
        // Implementation for Event Organizer logout
        this.loggedIn = false;
        System.out.println("Event Organizer " + name + " has logged out.");
        return !loggedIn;
    }

    // Specific Methods for Admin
    public boolean addEventOrganizer() { /* Implementation here */ return true; }
    public String generateEventReport() { /* Implementation here */ return "Report"; }

    // Other methods...
}
