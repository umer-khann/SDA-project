package com.example.controllers;

import com.example.oopfiles.*;
import com.example.JDBC.VenueDBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable {
    // FXML Fields
    public TextField EvName;
    public DatePicker EvDate;
    public TextField EVBudget;
    public TextField EvTopic;
    public TextField EvDuration;
    public TextField EvInstructor;
    public TextField EvPerformer;
    public TextField EvGenre;
    public TextField EvSpeaker;
    public TextField EvAgenda;
    public Button create;
    @FXML
    private TextField EVVenue;
    @FXML
    private AnchorPane Confrence;
    @FXML
    private AnchorPane WorkShop;
    @FXML
    private AnchorPane Concert;
    @FXML
    private ChoiceBox<String> Type;
    private int EvOrgID;
    private String[] eventTypes = {"WorkShop", "Concert", "Confrence"};

    // Instance of VenueDBHandler to validate venue
    private Venue ven;

    @FXML
    private TableView<Venue> VenueTable;
    @FXML
    private TableColumn<Venue, String> colVenueId;
    @FXML
    private TableColumn<Venue, String> colVenueName;
    @FXML
    private TableColumn<Venue, String> colLocation;
    @FXML
    private TableColumn<Venue, Integer> colCapacity;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Type.getItems().addAll(eventTypes);
        Type.setOnAction(this::getChoice);
        ven = VenueFactory.createVenue("INDOOR");
        ven.setDb(new VenueDBHandler());
        // Initialize the Venue Table columns
        initializeVenueTable();
        System.out.println(EvOrgID);
        // Set initial visibility
        WorkShop.setVisible(false);
        Concert.setVisible(false);
        Confrence.setVisible(false);

        create.setOnMouseClicked(event -> handleCreateEvent(null));

        // Load venue data
        //loadVenueData();
    }

    // Initialize Venue Table columns
    private void initializeVenueTable() {
        colVenueId.setCellValueFactory(new PropertyValueFactory<>("VenueId"));
        colVenueName.setCellValueFactory(new PropertyValueFactory<>("VenueName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
    }
    public void displayPushed(ActionEvent e){
        loadVenueData();
    }
    // Load venue data into the table
    private void loadVenueData() {
        ObservableList<Venue> venueList = FXCollections.observableArrayList();
        venueList.addAll(ven.getAllVenues()); // Replace with your DB fetching method
        VenueTable.setItems(venueList);
    }

    // Toggles visibility based on selected event type
    private void getChoice(ActionEvent e) {
        String selectedType = Type.getValue();
        if (Objects.equals(selectedType, eventTypes[0])) {
            WorkShop.setVisible(true);
            Concert.setVisible(false);
            Confrence.setVisible(false);
        } else if (Objects.equals(selectedType, eventTypes[1])) {
            WorkShop.setVisible(false);
            Concert.setVisible(true);
            Confrence.setVisible(false);
        } else if (Objects.equals(selectedType, eventTypes[2])) {
            WorkShop.setVisible(false);
            Concert.setVisible(false);
            Confrence.setVisible(true);
        }
    }

    @FXML
    private void handleCreateEvent(ActionEvent event) {
        // Get the event type selected by the user
        String eventType = Type.getValue();
        Event newEvent = null;

        // Validate that the event name is not empty
        String name = EvName.getText();
        if (name == null || name.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Event name cannot be empty.");
            return;
        }

        // Validate the event date
        LocalDate l = EvDate.getValue();
        if (l == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Event date must be selected.");
            return;
        }
        int month = l.getMonthValue();
        int year = l.getYear();
        int day = l.getDayOfMonth();
        String eventDate = String.format("%04d-%02d-%02d", year, month, day);

        // Validate budget input
        double budget = 0;
        try {
            budget = Double.parseDouble(EVBudget.getText());
            if (budget <= 0) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Budget must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid budget. Please enter a valid number.");
            return;
        }

        // Validate Venue I

        int venueID;
        try {
            venueID = Integer.parseInt(EVVenue.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid Venue ID. Please enter a valid number.");
            return;
        }
        if (!ven.venueExists(venueID)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Venue does not exist or is of the wrong type.");
            return;
        }

        // Handle Workshop event creation
        if (Objects.equals(eventType, eventTypes[0])) {
            String topic = EvTopic.getText();
            if (topic == null || topic.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Topic cannot be empty.");
                return;
            }

            double duration = 0;
            try {
                duration = Double.parseDouble(EvDuration.getText());
                if (duration <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Duration must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid duration. Please enter a valid number.");
                return;
            }

            String instructor = EvInstructor.getText();
            if (instructor == null || instructor.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Instructor name cannot be empty.");
                return;
            }

            newEvent = EventFactory.createEvent("WORKSHOP");
            newEvent.setEventName(name);
            newEvent.setEventDate(eventDate);
            newEvent.setBudget(budget);
            ((WorkshopEvent) newEvent).setTopic(topic);
            ((WorkshopEvent) newEvent).setDuration(duration);
            ((WorkshopEvent) newEvent).setInstructor(instructor);
        }
        // Handle Concert event creation
        else if (Objects.equals(eventType, eventTypes[1])) {
            String genre = EvGenre.getText();
            if (genre == null || genre.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Genre cannot be empty.");
                return;
            }

            String performer = EvPerformer.getText();
            if (performer == null || performer.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Performer name cannot be empty.");
                return;
            }

            newEvent = EventFactory.createEvent("CONCERT");
            newEvent.setEventName(name);
            newEvent.setEventDate(eventDate);
            newEvent.setBudget(budget);
            ((ConcertEvent) newEvent).setGenre(genre);
            ((ConcertEvent) newEvent).setPerformer(performer);
        }
        // Handle Conference event creation
        else if (Objects.equals(eventType, eventTypes[2])) {
            String agenda = EvAgenda.getText();
            if (agenda == null || agenda.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Agenda cannot be empty.");
                return;
            }

            String speaker = EvSpeaker.getText();
            if (speaker == null || speaker.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Speaker name cannot be empty.");
                return;
            }

            newEvent = EventFactory.createEvent("CONFERENCE");
            newEvent.setEventName(name);
            newEvent.setEventDate(eventDate);
            newEvent.setBudget(budget);
            ((ConferenceEvent) newEvent).setAgenda(agenda);
            ((ConferenceEvent) newEvent).setSpeaker(speaker);
        }

        // Save the created event to the database
        if (newEvent != null) {
            if (newEvent.createEvent(EvOrgID,venueID)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Event created and saved successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save the event.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Event creation failed due to invalid input.");
        }
    }

    // Helper method to show an alert box
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setEventOrgID(int eventOrgID) {
        this.EvOrgID = eventOrgID;
    }
}
