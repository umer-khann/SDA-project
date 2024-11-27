package com.example.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventOrganizerMainPageController implements Initializable {
    public VBox VBOX;
    public BorderPane mainpane;
    private int EventorgID;
    public JFXButton button1;
    public JFXButton button2;
    public JFXButton button3;
    public JFXButton button4;
    public JFXButton button5;
    public JFXButton button6;
    public JFXButton button7;
    public JFXButton button8;
    public JFXButton button9;
    public JFXButton button10;
    public String a;
    public String b;
    public String c;
    public String d;
    public String e1;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;

    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;

    @FXML
    private ScrollPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MenuClose.setVisible(false);
        Menu.setVisible(true);
        a = button1.getText();
        b = button2.getText();
        c = button3.getText();
        d = button4.getText();
        e1 = button5.getText();
        f = button6.getText();
        g = button7.getText();
        h = button8.getText();
        i = button9.getText();
        j = button10.getText();
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        slider.setTranslateX(-176); // Initial position
        // Open menu transition
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(0); // Move slider to the right (open position)
            slide.play();

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
                slider.setPrefWidth(176); // Sidebar width after open
                contentArea.setPrefWidth(400); // Content width after open

                // Reset the buttons
                button1.setText(a);
                button2.setText(b);
                button3.setText(c);
                button4.setText(d);
                button5.setText(e1);
                button6.setText(f);
                button7.setText(g);
                button8.setText(h);
                button9.setText(i);
                button10.setText(j);
            });
            VBOX.setPrefWidth(176);
        });

        // Close menu transition
        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(-176); // Move slider to the left (close position)
            slide.play();

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
                slider.setPrefWidth(0); // Sidebar width after close
                contentArea.setPrefWidth(730); // Content width after close

                // Clear buttons text when sidebar is closed
                button1.setText("");
                button2.setText("");
                button3.setText("");
                button4.setText("");
                button5.setText("");
                button6.setText("");
                button7.setText("");
                button8.setText("");
                button9.setText("");
                button10.setText("");
            });
            VBOX.setPrefWidth(0);
        });
    }


    private void loadPage(String fxml, int eventOrgID) {
        // Set the content area width to the full screen width initially
        contentArea.setPrefWidth(mainpane.getWidth()); // Adjust to full screen width

        // Start the transition to open the menu
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToX(-176); // Move slider to the left (close position)
        slide.play();

        // Hide the "Menu Close" label and show the "Menu" label
        MenuClose.setVisible(false);
        Menu.setVisible(true);

        // Transition finished actions
        slide.setOnFinished((ActionEvent e) -> {
            slider.setPrefWidth(0); // Sidebar width after close
            contentArea.setPrefWidth(mainpane.getWidth()); // Ensure content width fills screen after close

            // Clear buttons text when sidebar is closed
            button1.setText("");
            button2.setText("");
            button3.setText("");
            button4.setText("");
            button5.setText("");
            button6.setText("");
            button7.setText("");
            button8.setText("");
            button9.setText("");
            button10.setText("");
        });

        // Set content area visible and reset its width
        contentArea.setVisible(true); // Ensure content area takes full width when sidebar is open

        // Set content area to start from the full screen width when transitioning
        contentArea.setPrefWidth(mainpane.getWidth()); // Set the initial content area width to screen width

        // Load the appropriate FXML and set the controller's EventOrgID
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/" + fxml));
            Parent pane = loader.load();

            // Access the controller and set the EventOrgID
            Object controller = loader.getController();

            if (controller instanceof CreateEventController) {
                ((CreateEventController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof ManageEventController) {
                ((ManageEventController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof HandleEventBudgetController) {
                ((HandleEventBudgetController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof AddNewVenueController) {
                ((AddNewVenueController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof RemoveVenueController) {
                ((RemoveVenueController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof ManageAttendeeController) {
                ((ManageAttendeeController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof AddSponsershipController) {
                ((AddSponsershipController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof RemoveSponsorshipController) {
                ((RemoveSponsorshipController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof SelectEventController) {
                ((SelectEventController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof AllocateAndTrackEventResourcesController) {
                ((AllocateAndTrackEventResourcesController) controller).setEventOrgID(eventOrgID);
            } else if (controller instanceof EventOrgNotifications) {
                ((EventOrgNotifications) controller).setEventOrgID(eventOrgID);
            }

            // Set the loaded FXML pane as the content of the content area
            contentArea.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace(); // Log the error if loading fails
        }
    }



    // Example event handlers for the buttons in the sidebar
    @FXML
    private void HandleCrEv(ActionEvent event) {
        loadPage("Create-event.fxml",EventorgID); // Load Dashboard page
    }

    @FXML
    private void handleAddClick(ActionEvent event) {
        loadPage("attendee-reg.fxml",EventorgID); // Load Add page
    }

    public void ManageEventClick(ActionEvent actionEvent) {
        loadPage("manage-event.fxml",EventorgID);
    }

    public void ManageAttendeeClick(ActionEvent actionEvent) {
        loadPage("select-event.fxml",EventorgID);
    }

    public void RemoveSponsorshipClick(ActionEvent actionEvent) {
        loadPage("remove-sponsorship.fxml",EventorgID);
    }

    public void budgetClick(ActionEvent actionEvent) {
        loadPage("handle-event-budget.fxml",EventorgID);
    }

    public void AddVenueClick(ActionEvent actionEvent) {
        loadPage("add-new-venue.fxml",EventorgID);
    }

    public void HandleRemVen(ActionEvent actionEvent) { loadPage("remove-venue.fxml",EventorgID);}

    public void HandleAddSp(ActionEvent actionEvent) {
        loadPage("add-sponsership.fxml",EventorgID);
    }

    public void eventresources(ActionEvent actionEvent) {
        loadPage("Allocate-And-Track-Event-Resources.fxml",EventorgID);
    }

    public void setEventorgID(int id) {
        this.EventorgID=id;
    }

    public void EvOrgNotClick(ActionEvent actionEvent) {
        loadPage("Event-Org-Notifications.fxml",EventorgID);

    }

    public void LogOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sdaproj/home-page.fxml"));
        BorderPane root = fxmlLoader.load(); // Assuming the root node is a BorderPane
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("");
        stage.show();
    }
}

