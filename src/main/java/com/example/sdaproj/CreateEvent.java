package com.example.sdaproj;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Date;
import java.util.List;

public class CreateEvent extends Application {
    double x,y = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Create-event.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setScene(new Scene(root, 700
                , 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public abstract class Event {
        private int eventID;
        private String eventName;
        private Date eventDate;
        private boolean status;
        private double budget;

        public Event(int eventID, String eventName, Date eventDate, double budget) {
            this.eventID = eventID;
            this.eventName = eventName;
            this.eventDate = eventDate;
            this.budget = budget;
            this.status = false; // Default to inactive
        }

        public boolean updateDetails(String newName, Date newDate) {
            this.eventName = newName;
            this.eventDate = newDate;
            return true;
        }

        public boolean allocateBudget(double newBudget) {
            if (newBudget >= 0) {
                this.budget = newBudget;
                return true;
            }
            return false;
        }

        // Getters and setters
        public int getEventID() {
            return eventID;
        }

        public String getEventName() {
            return eventName;
        }

        public Date getEventDate() {
            return eventDate;
        }

        public double getBudget() {
            return budget;
        }

        public boolean isActive() {
            return status;
        }

        public void activate() {
            this.status = true;
        }

        public void deactivate() {
            this.status = false;
        }
    }

    public class WorkshopEvent extends Event {
        private String topic;
        private double duration; // In hours
        private String instructor;

        public WorkshopEvent(int eventID, String eventName, Date eventDate, double budget, String topic, double duration, String instructor) {
            super(eventID, eventName, eventDate, budget);
            this.topic = topic;
            this.duration = duration;
            this.instructor = instructor;
        }

        // Getters and setters
        public String getTopic() {
            return topic;
        }

        public double getDuration() {
            return duration;
        }

        public String getInstructor() {
            return instructor;
        }
    }


    public class ConcertEvent extends Event {
        private List<String> performerNames;
        private String genre;

        public ConcertEvent(int eventID, String eventName, Date eventDate, double budget, List<String> performerNames, String genre) {
            super(eventID, eventName, eventDate, budget);
            this.performerNames = performerNames;
            this.genre = genre;
        }

        // Getters and setters
        public List<String> getPerformerNames() {
            return performerNames;
        }

        public String getGenre() {
            return genre;
        }
    }




}
