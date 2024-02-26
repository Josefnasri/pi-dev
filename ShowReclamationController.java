package controllers;

import entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceReclamation;
import tn.esprit.reclamation.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowReclamationController {
    @FXML
    private FlowPane reclamationContainer;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    public void initialize() throws SQLException {
        List<Reclamation> reclamations = serviceReclamation.afficher();
        displayReclamations(reclamations);
    }

    private void displayReclamations(List<Reclamation> reclamations) {
        for (Reclamation reclamation : reclamations) {
            VBox card = createReclamationCard(reclamation);
            reclamationContainer.getChildren().add(card);
        }
    }

    private VBox createReclamationCardFunction(Reclamation reclamation) {
        VBox card = new VBox();
        card.getStyleClass().add("reclamation-card");

        Label requestIdLabel = new Label("Request ID: " + reclamation.getRequest_id());
        Label requestDateLabel = new Label("Request Date: " + reclamation.getRequest_date().toString());
        Label customerIdLabel = new Label("Customer ID: " + reclamation.getCustomer_id());
        Label descriptionLabel = new Label("Description: " + reclamation.getDescription());
        Label statusLabel = new Label("Status: " + reclamation.getStatus());

        card.getChildren().addAll(requestIdLabel, requestDateLabel, customerIdLabel, descriptionLabel, statusLabel);

        card.setOnMouseClicked(event -> openReclamationDetailsPage(reclamation));
        card.setCursor(Cursor.HAND);

        return card;
    }

    private VBox createReclamationCard(Reclamation reclamation) {
        VBox card = new VBox();
        card.getStyleClass().add("reclamation-card");
        Label requestIdLabel = new Label("Request ID: " + reclamation.getRequest_id());
        Label requestDateLabel = new Label("Request Date: " + reclamation.getRequest_date().toString());
        Label customerIdLabel = new Label("Customer ID: " + reclamation.getCustomer_id());
        Label descriptionLabel = new Label("Description: " + reclamation.getDescription());
        Label statusLabel = new Label("Status: " + reclamation.getStatus());
        card.getChildren().addAll(requestIdLabel, requestDateLabel, customerIdLabel, descriptionLabel, statusLabel);
        card.setOnMouseClicked(event -> openReclamationDetailsPage(reclamation));
        card.setCursor(Cursor.HAND);
        return card;
    }


    private void openReclamationDetailsPage(Reclamation reclamation) {
        reclamationContainer.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowReclamationDetails.fxml"));
                Parent root = fxmlLoader.load();
                ShowReclamationDetails controller = fxmlLoader.getController();
                controller.displayReclamationDetails(reclamation);
                Scene scene = new Scene(root);
                Stage stage = (Stage) reclamationContainer.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Reclamation Details");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void OnRefresh(ActionEvent actionEvent) throws SQLException {
        List<Reclamation> reclamations = serviceReclamation.afficher();
        reclamationContainer.getChildren().clear();
        displayReclamations(reclamations);
    }

    public void OnAjouter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AjoutReclamation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Ajouter Reclamation");
        stage.setScene(scene);
        stage.show();
    }

    public void OnReponse(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowReponse.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Liste Reponse");
        stage.setScene(scene);
        stage.show();
    }
}
