package com.example.controllers;

import com.example.oopfiles.EventFactory;
import com.example.oopfiles.Feedback;
import com.example.oopfiles.Event;
import com.example.JDBC.FeedbackDBHandler;
import com.example.oopfiles.WorkshopEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Date;

public class ProvideFeedBackController implements Initializable {

    public TextField EvID;
    @FXML
    private TableColumn<Event, Integer> ColEvID;
    @FXML
    private TableColumn<Event, String> ColEvName;
    @FXML
    private TableColumn<Event, Date> ColEvDate;
    @FXML
    private TableView<Event> Table;
    @FXML
    private ImageView star1, star2, star3, star4, star5;
    @FXML
    private TextArea Comments;  // Text area to enter comments
    @FXML
    private Button submitButton;        // Submit button for feedback
    private Event ev = EventFactory.createEvent("WORKSHOP");
    private Image on, off;
    private int AttendeeID;
    private int rating = 0; // Default rating
    private Feedback feedback = new Feedback();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load star images
        URL onImageUrl = getClass().getResource("/com/example/sdaproj/images/star.png");
        URL offImageUrl = getClass().getResource("/com/example/sdaproj/images/star-off.png");

        if (onImageUrl != null && offImageUrl != null) {
            on = new Image(onImageUrl.toExternalForm());
            off = new Image(offImageUrl.toExternalForm());
        } else {
            System.out.println("Image resources not found. Check your paths.");
            return;
        }

        // Initialize rating functionality
        star1.setOnMouseClicked(event -> setRating(1));
        star2.setOnMouseClicked(event -> setRating(2));
        star3.setOnMouseClicked(event -> setRating(3));
        star4.setOnMouseClicked(event -> setRating(4));
        star5.setOnMouseClicked(event -> setRating(5));

        initializeTable();
    }

    private void setRating(int rating) {
        this.rating = rating;
        updateStarImages();
    }

    private void updateStarImages() {
        star1.setImage(rating >= 1 ? on : off);
        star2.setImage(rating >= 2 ? on : off);
        star3.setImage(rating >= 3 ? on : off);
        star4.setImage(rating >= 4 ? on : off);
        star5.setImage(rating >= 5 ? on : off);
    }

    private void initializeTable() {
        ColEvID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        ColEvName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        ColEvDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
    }

    public void DisplayPushed(ActionEvent e) {
        initializeTable();
        loadVenueData();
    }

    private void loadVenueData() {
        ObservableList<Event> evList = FXCollections.observableArrayList();
        // Load events associated with the attendee
        evList.addAll(ev.ListOfEvents(AttendeeID)); // Replace with your DB fetching method
        Table.setItems(evList);
    }

    public void ProvideFeedBackPushed(ActionEvent actionEvent) {
        // Get the event ID from the TextField
        String eventIDStr = EvID.getText().trim();

        if (eventIDStr.isEmpty()) {
            showAlert("Feedback Error", "Please provide a valid Event ID.");
            return;
        }

        // Try to parse the event ID to an integer
        int eventID;
        try {
            eventID = Integer.parseInt(eventIDStr);
        } catch (NumberFormatException e) {
            showAlert("Feedback Error", "Please provide a valid numeric Event ID.");
            return;
        }

        // Fetch the event using the eventID
        boolean f = ev.EventExistsByAttendee(AttendeeID, eventID);
        if (f && rating > 0) {
            String comments = Comments.getText().trim();

            if (comments.isEmpty()) {
                // Optional: Alert if comments are empty (can be skipped if comments are not mandatory)
                showAlert("Feedback Error", "Please provide your comments.");
                return;
            }

            // Create the Feedback object
            Feedback feedback = new Feedback(0, rating, comments, new java.util.Date());
            feedback.setEventID(eventID); // Link feedback to selected event

            // Insert feedback into the database
            boolean success = feedback.insertFeedback();

            if (success) {
                showAlert("Feedback Submitted", "Your feedback has been submitted successfully.");
            } else {
                showAlert("Feedback Error", "There was an issue submitting your feedback.");
            }

            // Reset rating and comments field after submission
            resetFeedbackForm();
        } else {
            showAlert("Feedback Error", "Please provide a valid rating and event ID.");
        }
    }
    private void resetFeedbackForm() {
        rating = 0;
        updateStarImages();
        Comments.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setAttendeeID(int attendeeID) {
        this.AttendeeID = attendeeID;
    }
}
