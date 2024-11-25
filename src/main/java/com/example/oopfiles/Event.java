package com.example.oopfiles;

import com.example.JDBC.EventDBController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.List;

public abstract class Event {
    private int eventID;
    private String eventName;
    private String eventDate;
    private boolean status;
    private double budget;
    protected static EventDBController db = new EventDBController();;
    private String eventType;
    private EventOrganizer organizer;  // Aggregation: Event has an organizer
    private Venue v;
    private int seats;
    private int staff;
    private int NoOfTechnicalEquipment;
    private static ObservableList<Attendee> attendeeList;

    public Event() {
        attendeeList = FXCollections.observableArrayList();
    }


    public static void deleteEvent(int eventID) {
    }



    public static ObservableList<Event> initializeTableForOrganizer(ObservableList<Event> eventList, int currentOrganizerid) {
        db.showCompleteEvents(eventList,currentOrganizerid);
        return eventList;
    }

    public static ObservableList<Event> initializeTableForAttendee(ObservableList<Event> eventList) {
        db.showEventsForAttendee(eventList);
        return eventList;
    }

    public static boolean updateEvent(int eventID, String newName, Date newDate)
    {
        return db.updateEvent(eventID,newName,newDate);
    }

    public static void insertEventUpdateNotification(int eventID, int organizerUserID, String message) {
        db.insertEventUpdateNotification(eventID,organizerUserID,message);
    }

    public static void updateEventDetails(int eventid, Integer staff1, Integer seats1, Integer equipment1) throws Exception {
        db.updateEventResources(eventid,staff1,seats1,equipment1);
    }


    public boolean updateDetails(String newName, String newDate) {
        this.eventName = newName;
        this.eventDate = newDate;
        this.eventType="";
        return true;
    }

    public static boolean checkEvent(int ID, int OrgID){
        boolean result = db.verifyEvent(ID, OrgID);
        return result;
    } public static boolean checkEvent(int ID){
        boolean result = db.verifyEvent(ID);
        return result;
    }
    public static ObservableList<Attendee> displayAttendees(int eventID){
        db.retrieveAttendees(attendeeList,eventID);
        return attendeeList;
    }

    public static void updateBudget(int ID,float newBudget){
        db.updateEventBudget(ID,newBudget);
    }

    public static ObservableList<Event> intializeTable(ObservableList<Event> eventList,int EventOrgID){
        db.showEvents(eventList,EventOrgID);
        return eventList;
    }

    public static ObservableList<Event> intializeTableForResources(ObservableList<Event> eventList,int EventOrgID){
        db.showEventResources(eventList,EventOrgID);
        return eventList;
    }

    public void updateEventResources(int eventID,Integer staff,Integer  seats, Integer equipment) throws Exception {
        db.updateEventResources(eventID,staff,seats,equipment);
    }

    public boolean allocateBudget(double newBudget) {
        if (newBudget >= 0) {
            this.budget = newBudget;
            return true;
        }
        return false;
    }

    public abstract boolean createEvent(int a, int b);

    public abstract boolean createEvent();
    // Getters and setters
    public int getEventID() {
        return eventID;
    }

    public void assignValues(int eventID,String eventName,float budget,String eventType){
        this.eventID=eventID;
        this.eventName=eventName;
        this.budget=budget;
        this.eventType=eventType;
    }
    public static boolean EventExistsByOrganizer(int EVID, int OrgID){
        return db.EventExistsByAttendee(OrgID,EVID);
    }
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public boolean isActive() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void activate() {
        this.status = true;
    }

    public void deactivate() {
        this.status = false;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public EventOrganizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(EventOrganizer organizer) {
        this.organizer = organizer;
    }

    public void assignAllValues(int eventID, String eventName, float budget, String eventType, String eventDate, boolean status) {
        this.eventID=eventID;
        this.eventName=eventName;
        this.budget=budget;
        this.eventType=eventType;
        this.eventDate=eventDate;
        this.status=status;
    }

    public int getOrganizerID() {
        return organizer.getID(organizer.getUsername());
    }

    public void setVenue(Venue venue) {
        this.v = venue;
    }

    public String getVenueName() {
        return v != null ? v.getVenueName() : "No Venue Assigned";
    }

    public String getVenueLocation() {
        return v != null ? v.getLocation() : "No Location Assigned";
    }

    public ObservableList<Attendee> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(ObservableList<Attendee> attendeeList) {
        this.attendeeList = attendeeList;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
    }

    public int getNoOfTechnicalEquipment() {
        return NoOfTechnicalEquipment;
    }

    public void setNoOfTechnicalEquipment(int noOfTechnicalEquipment) {
        NoOfTechnicalEquipment = noOfTechnicalEquipment;
    }


    public List<Event> ListOfEvents(int attendeeID) {
        return db.DisplayEventsByAttendee(attendeeID);
    }
    public static boolean EventExistsByAttendee(int attendeeID, int EVID){
        return db.EventExistsByAttendee(attendeeID,EVID);
    }

    // Placeholder method for saving event to database
}
