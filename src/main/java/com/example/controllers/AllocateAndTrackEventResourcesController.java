package com.example.controllers;

import com.example.oopfiles.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AllocateAndTrackEventResourcesController {

    private int EventOrgID;

    @FXML
    private TextField eventid;
    @FXML
    private TextField equipment;
    @FXML
    private TextField staff;
    @FXML
    private TextField seats;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, Integer> colEventID;

    @FXML
    private TableColumn<Event, String> colEventName;

    @FXML
    private TableColumn<Event, Integer> Staff;
    @FXML
    private TableColumn<Event, Integer> Seats;
    @FXML
    private TableColumn<Event, Integer> Equipment;



    @FXML
    private TableColumn<Event, Integer> colEventType;

    @FXML
    public void buttonpress(ActionEvent actionEvent) {
        // Get the eventID from the TextField
        String eventIDText = eventid.getText();

        // Validate the input (check if the eventID is a valid integer)
        int eventID;
        try {
            eventID = Integer.parseInt(eventIDText);
        } catch (NumberFormatException e) {
            // Show an error message if the eventID is not a valid number
            System.out.println("Please enter a valid event ID.");
            return;  // Exit if the eventID is invalid
        }

        // Initialize variables for staff, seats, and equipment with null or 0
        Integer staff1 = null;
        Integer seats1 = null;
        Integer equipment1= null;

        // Retrieve the values from the text fields (staff, seats, and equipment)
        String staffText = staff.getText();
        String seatsText = seats.getText();
        String equipmentText = equipment.getText();

        // Set values for staff, seats, and equipment if the text fields are not empty
        if (!staffText.isEmpty()) {
            try {
                staff1 = Integer.parseInt(staffText);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for staff.");
                return;
            }
        }
        if (!seatsText.isEmpty()) {
            try {
                seats1 = Integer.parseInt(seatsText);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for seats.");
                return;
            }
        }
        if (!equipmentText.isEmpty()) {
            try {
                equipment1 = Integer.parseInt(equipmentText);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for equipment.");
                return;
            }
        }

        // Update the event resources in the database

        // Optionally, find the event in the table and update its values
       // Event event = findEventById(eventID);
       // if (event != null) {
      //      if (staff != null) event.setStaff(staff);
       //     if (seats != null) event.setSeats(seats);
      //      if (equipment != null) event.setEquipment(equipment);
        //updateEventResources(eventID, staff, seats, equipment);

            // Refresh the TableView to reflect the changes
      //      eventTable.refresh();

            // Optionally, show a success message
     //       System.out.println("Event resources updated successfully.");
    //    } else {
    //        System.out.println("Event not found with eventID: " + eventID);
  //      }
    }


    public void RFA(ActionEvent actionEvent) {
        // Link columns to Event model properties

        colEventID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        colEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        Staff.setCellValueFactory(new PropertyValueFactory<>("Staff"));
        Seats.setCellValueFactory(new PropertyValueFactory<>("Seats"));
        Equipment.setCellValueFactory(new PropertyValueFactory<>("NoOfTechnicalEquipment"));

        // Load data from the database
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        eventTable.setItems(Event.intializeTableForResources(eventList,EventOrgID));
    }

    public void setEventOrgID(int eventOrgID) {
        this.EventOrgID=eventOrgID;
    }
}
