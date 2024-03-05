package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;
import tn.esprit.crud.models.event;
import tn.esprit.crud.services.Payment;
import tn.esprit.crud.services.eventService;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class homeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    GridPane gridPane = new GridPane();

    @FXML
    private Pagination pagination;


    @FXML
    void showEvents(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherEvent.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("evenement");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showParticipant(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherParticipant.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Afficher Participant");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'home.fxml'.";
        loadEvents();

    }

    private void loadEvents() {
        eventService eventService = new eventService();
        List<event> events = eventService.readAll();

        pagination.setPageCount((int) Math.ceil((double) events.size() / 4)); // Each page will have at most 4 cards

        pagination.setPageFactory(pageIndex -> {
            AnchorPane pageAnchorPane = new AnchorPane();
            pageAnchorPane.setPrefWidth(pagination.getPrefWidth());
            pageAnchorPane.setPrefHeight(pagination.getPrefHeight());

            int startIndex = pageIndex * 4;
            int endIndex = Math.min(startIndex + 4, events.size());

            int row = 0;
            int col = 0;
            for (int i = startIndex; i < endIndex; i++) {
                event currentEvent = events.get(i);
                AnchorPane eventCard = createEventCard(currentEvent);

                eventCard.setLayoutX(col * 320); // Adjusting x-coordinate to display 2 cards per row
                eventCard.setLayoutY(row * 170); // Adjusting y-coordinate

                pageAnchorPane.getChildren().add(eventCard);

                col++;
                if (col == 2) { // Move to the next row after displaying 2 cards
                    col = 0;
                    row++;
                }
            }

            return pageAnchorPane;
        });
    }

    private AnchorPane createEventCard(event event) {
        AnchorPane card = new AnchorPane();
        card.setPrefWidth(300); // Adjusted width for the split card
        card.setPrefHeight(150); // Adjusted height

        // Create a rounded rectangle as the background of the card
        Rectangle background = new Rectangle(300, 150); // Adjusted height
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setFill(Color.LIGHTGREEN);

        // Create labels to display event information
        Label nameLabel = new Label("Name: " + event.getNom());
        nameLabel.setLayoutX(110); // Left side with padding and offsetX
        nameLabel.setLayoutY(10);

        Label dateLabel = new Label("Date: " + event.getDate().toString());
        dateLabel.setLayoutX(110);
        dateLabel.setLayoutY(40);

        Label addressLabel = new Label("Address: " + event.getAdresse());
        addressLabel.setLayoutX(110);
        addressLabel.setLayoutY(130);

        Label prixLabel = new Label("Prix: " + event.getPrix()+"DT");
        prixLabel.setLayoutX(110);
        prixLabel.setLayoutY(70);

        Label rateLabel = new Label("Rate: " + event.getRate()+"/5");
        rateLabel.setLayoutX(110);
        rateLabel.setLayoutY(100);

        // Create ImageView for the event image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(150); // Adjusted height
        imageView.setLayoutX(0); // Right side with padding and offsetX
        imageView.setLayoutY(0);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-background-radius: 20;");

        URL imageURL = getClass().getResource("/upload/events/" + event.getImage());
        // Load image if available
        if (!Objects.equals(event.getImage(), "") && imageURL != null) {
            imageView.setImage(new Image(imageURL.toString(), 300, 150, true, true));
        }else {
            imageURL = getClass().getResource("/assets/no_image.jpg");
            imageView.setImage(new Image(imageURL.toString(), 300, 150, true, true));
        }
        card.setOnMouseClicked(e -> {
            // Open a new scene with WebView, event information, rating, payment, and print buttons
            openEventDetailsScene(event);
        });
        // Add elements to the card
        card.getChildren().addAll(background, nameLabel, dateLabel, addressLabel, rateLabel, prixLabel,imageView);

        return card;
    }

    private void openEventDetailsScene(event event) {

        Stage primaryStage = new Stage();
        // Create VBox for event details and buttons
        VBox detailsBox = new VBox(10);
        detailsBox.setAlignment(Pos.CENTER_LEFT);

        // Example: Create labels for event details
        Label eventNameLabel = new Label("Event Name: " + event.getNom());
        Label eventDateLabel = new Label("Date: " + event.getDate().toString());
        Label eventAddressLabel = new Label("Address: " + event.getAdresse());
        Label eventPriceLabel = new Label("Price: " + event.getPrix()+"DT");
        Label eventRatingLabel = new Label("Rating: " + event.getRate() + "/5");

        // Style labels
        eventNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        eventDateLabel.setStyle("-fx-font-size: 14px;");
        eventAddressLabel.setStyle("-fx-font-size: 14px;");
        eventPriceLabel.setStyle("-fx-font-size: 14px;");
        eventRatingLabel.setStyle("-fx-font-size: 14px;");

        // Create buttons
        Button payButton = new Button("Buy Ticket");
        payButton.setOnMouseClicked(e->{
            primaryStage.close();

            Payment payment = new Payment();
            payment.processPayment((long) (event.getPrix()*0.34));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Processing");
            alert.setHeaderText(null);
            alert.setContentText("Your payment is being processed by Stripe. Please wait for up to 48 hours for confirmation.");
            alert.show();
        });

        // Style buttons
        payButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        // Add labels and buttons to detailsBox
        detailsBox.getChildren().addAll(eventNameLabel, eventDateLabel, eventAddressLabel, eventPriceLabel, eventRatingLabel, payButton);

        // Create ImageView for event image

        ImageView imageView = new ImageView(); // Specify your image path

        URL imageURL = getClass().getResource("/upload/events/" + event.getImage());
        // Load image if available
        if (!Objects.equals(event.getImage(), "") && imageURL != null) {
            imageView.setImage(new Image(imageURL.toString(), 300, 150, true, true));
        }else {
            imageURL = getClass().getResource("/assets/no_image.jpg");
            imageView.setImage(new Image(imageURL.toString(), 300, 150, true, true));
        }
        imageView.setFitWidth(200); // Set width of the image
        imageView.setPreserveRatio(true); // Preserve image aspect ratio

        // Create WebView for map view
        WebView mapView = new WebView();
        WebEngine webEngine = mapView.getEngine();
        webEngine.load("https://www.google.com/maps?q=" + event.getLocalisation()); // Load Google Maps with latitude and longitude
        mapView.setPrefSize(300, 800); // Set preferred size for WebView

        // Create GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); // Set horizontal gap between nodes
        gridPane.setVgap(10); // Set vertical gap between nodes

        // Add nodes to GridPane
        gridPane.add(mapView, 0, 0); // Add mapView to column 0, row 0
        gridPane.add(detailsBox, 1, 0); // Add detailsBox to column 1, row 0
        gridPane.add(imageView, 2, 0); // Add imageView to column 2, row 0

        // Create scene
        Scene scene = new Scene(gridPane, 800, 400);

        // Set stage properties
        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Details");
        primaryStage.show();
    }

    @FXML
    void showCallender(ActionEvent event) {
// Create Agenda
        Agenda agenda = new Agenda();

        // Load events into the agenda
        loadEventsIntoAgenda(agenda);

        // Create Stage
        Stage calendarStage = new Stage();
        calendarStage.setScene(new Scene(agenda, 800, 600));
        calendarStage.setTitle("Event Calendar");
        calendarStage.show();
    }
    private void loadEventsIntoAgenda(Agenda agenda) {
        // Fetch events from your data source
        List<event> events = eventService.readAllStatic();

        // Add events to the agenda
        for (event event : events) {
            System.out.println(event.getDate());

            // Convert java.sql.Date to java.util.Date
            java.util.Date utilDate = new java.util.Date(event.getDate().getTime());

            // Convert java.util.Date to LocalDate
            Instant instant = utilDate.toInstant();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            LocalDate localDate = localDateTime.toLocalDate();

            LocalDateTime startDateTime = localDate.atStartOfDay();
            LocalDateTime endDateTime = startDateTime.plusHours(1); // Assuming events are one hour long

            Agenda.Appointment appointment = new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(startDateTime)
                    .withEndLocalDateTime(endDateTime)
                    .withSummary(event.getNom());
            agenda.appointments().add(appointment);
        }
    }

}
