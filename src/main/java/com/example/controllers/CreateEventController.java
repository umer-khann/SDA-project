package com.example.controllers;

import com.example.oopfiles.Event;
import com.example.oopfiles.WorkshopEvent;
import com.example.oopfiles.ConferenceEvent;
import com.example.oopfiles.ConcertEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.util.Date.*;

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
    private ImageView Exit;
    @FXML
    private AnchorPane Confrence;
    @FXML
    private AnchorPane WorkShop;
    @FXML
    private AnchorPane Concert;
    @FXML
    private ChoiceBox<String> Type;

    private String[] eventTypes = {"WorkShop", "Concert", "Confrence"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Type.getItems().addAll(eventTypes);
        Type.setOnAction(this::getChoice);

        // Set initial visibility
        WorkShop.setVisible(true);
        Concert.setVisible(false);
        Object ev;
        create.setOnMouseClicked(event -> {

        });
        Exit.setOnMouseClicked(event -> System.exit(0));
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
            System.out.println("Event name cannot be empty.");
            return;  // Early return on error
        }

        // Validate the event date
        LocalDate l = EvDate.getValue();
        if (l == null) {
            System.out.println("Event date must be selected.");
            return;  // Early return on error
        }
        int month = l.getMonthValue();  // Get the numeric value of the month (1-12)
        int year = l.getYear();         // Get the year
        int day = l.getDayOfMonth();
        String eventDate = String.format("%04d-%02d-%02d", year, month, day);

        // Validate budget input
        double budget = 0;
        try {
            budget = Double.parseDouble(EVBudget.getText());
            if (budget <= 0) {
                System.out.println("Budget must be a positive number.");
                return;  // Early return on error
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid budget. Please enter a valid number.");
            return;  // Early return on error
        }

        // Handle Workshop event creation
        if (Objects.equals(eventType, eventTypes[0])) {
            String topic = EvTopic.getText();
            if (topic == null || topic.trim().isEmpty()) {
                System.out.println("Topic cannot be empty.");
                return;
            }

            double duration = 0;
            try {
                duration = Double.parseDouble(EvDuration.getText());
                if (duration <= 0) {
                    System.out.println("Duration must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid duration. Please enter a valid number.");
                return;
            }

            String instructor = EvInstructor.getText();
            if (instructor == null || instructor.trim().isEmpty()) {
                System.out.println("Instructor name cannot be empty.");
                return;
            }

            newEvent = new WorkshopEvent();
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
                System.out.println("Genre cannot be empty.");
                return;
            }

            String performer = EvPerformer.getText();
            if (performer == null || performer.trim().isEmpty()) {
                System.out.println("Performer name cannot be empty.");
                return;
            }

            newEvent = new ConcertEvent();
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
                System.out.println("Agenda cannot be empty.");
                return;
            }

            String speaker = EvSpeaker.getText();
            if (speaker == null || speaker.trim().isEmpty()) {
                System.out.println("Speaker name cannot be empty.");
                return;
            }

            newEvent = new ConferenceEvent();
            newEvent.setEventName(name);
            newEvent.setEventDate(eventDate);
            newEvent.setBudget(budget);
            ((ConferenceEvent) newEvent).setAgenda(agenda);
            ((ConferenceEvent) newEvent).setSpeaker(speaker);
        }

        // Save the created event to the database
        if (newEvent != null) {
            if (newEvent.createEvent()) {
                System.out.println("Event created and saved successfully!");
            } else {
                System.out.println("Failed to save the event.");
            }
        } else {
            System.out.println("Event creation failed due to invalid input.");
        }
    }

}
