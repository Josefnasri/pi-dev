package Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import service.eventService;

public class homeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pagination pagination;

    @FXML
    void showEvents(MouseEvent event) {

    }

    @FXML
    void showParticipant(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'home.fxml'.";
        loadEvents();
    }

    private void loadEvents() {
        eventService eventService = new eventService();

        List<event> events = eventService.readAll();

        pagination.setPageCount((int) Math.ceil((double) events.size() / 4)); // Each page will have at most 6 cards

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

                eventCard.setLayoutX(col * 320); // Adjusting x-coordinate to display 3 cards per row
                eventCard.setLayoutY(row * 170); // Adjusting y-coordinate

                pageAnchorPane.getChildren().add(eventCard);

                col++;
                if (col == 2) { // Move to the next row after displaying 3 cards
                    col = 0;
                    row++;
                }
            }

            return pageAnchorPane;
        });
    }

    private AnchorPane createEventCard(event event) {
        AnchorPane card = new AnchorPane();
        card.setPrefWidth(300);
        card.setPrefHeight(150);

        // Create a rounded rectangle as the background of the card
        Rectangle background = new Rectangle(300, 120);
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setFill(Color.LIGHTGREEN);

        // Example: Create labels to display event information
        Label nameLabel = new Label(event.getNom());
        nameLabel.setLayoutX(20);
        nameLabel.setLayoutY(20);

        Label dateLabel = new Label(event.getDate().toString());
        dateLabel.setLayoutX(20);
        dateLabel.setLayoutY(50);

        Label addressLabel = new Label(event.getAdresse());
        addressLabel.setLayoutX(20);
        addressLabel.setLayoutY(80);

        // Participate Button
        Button participateButton = new Button("Participate");
        participateButton.setLayoutX(200);
        participateButton.setLayoutY(90);
        participateButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Coming Soon");
            alert.setHeaderText(null);
            alert.setContentText("Participate function is coming soon!");
            alert.showAndWait();
        });

        card.getChildren().addAll(background, nameLabel, dateLabel, addressLabel, participateButton);

        return card;
    }
}
