package com.example.oopfiles;

public class Admin extends User {
    private static final User INSTANCE = new Admin();

    private static final String HARD_CODED_USERNAME = "umer";
    private static final String HARD_CODED_PASSWORD = "umer";

    private Admin() {
        super(1,"Umer Khan","umer.2003@gmail.com","03005560602");
        this.setUserName(HARD_CODED_USERNAME);
        this.setPassword(HARD_CODED_PASSWORD);
    }

    public static User getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean login(String uname, String pass) {
        if (HARD_CODED_USERNAME.equals(uname) && HARD_CODED_PASSWORD.equals(pass)) {
            this.loggedIn = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean logout() {
        this.loggedIn = false;
        return true;
    }

    @Override
    public boolean isValidUserName() {
        return false;
    }

    @Override
    public boolean isValidEmail() {
        return false;
    }
    @Override
    public void addNotification(int ID, int userType, String message, String notifType){}

    @Override
    public void Create(String username, String password) {

    }

    ;

}
