package com.example.oopfiles;
public abstract class User {
    protected int userID;
    protected String name;
    protected String email;
    protected String contactDetails;
    protected boolean loggedIn;
    private String userName;
    private String password;

    // Constructor
    public User() {
        this.userID = 0; // Default ID
        this.name = "Default User"; // Default name
        this.email = "default@example.com"; // Default email
        this.contactDetails = "000-000-0000"; // Default contact details
        this.loggedIn = false; // Default login state
    }

    //parameterized constructor
    public User(int userID, String name, String email, String contactDetails) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.contactDetails = contactDetails;
        this.loggedIn = false;
    }


    // Common Getter and Setter Methods
    public int getID(String username) {
        return 0;
    }


    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
    public abstract boolean login(String uname, String pass);
    public abstract boolean logout();
    public abstract boolean isValidUserName();
    public abstract boolean isValidEmail();
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void addNotification(int ID, int userType, String message, String notifType) {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactDetails() { return contactDetails; }


    public void setContactDetails(String contactDetails) { this.contactDetails = contactDetails; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExperienceLevel(int experienceLevel) {}

    public void registerEventOrganizer() {}

    public abstract void Create(String username, String password);

    public String getUsername() {
        return this.userName;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {}
}
