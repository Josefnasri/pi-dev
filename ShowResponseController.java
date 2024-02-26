package controllers;

import entities.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceResponse;
import tn.esprit.reclamation.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowResponseController {
    @FXML
    private FlowPane responseContainer;

    private ServiceResponse serviceResponse = new ServiceResponse();

    public void initialize() throws SQLException {
        List<Response> responses = serviceResponse.afficher();
        displayResponses(responses);
    }

    private void displayResponses(List<Response> responses) {
        for (Response response : responses) {
            VBox card = createResponseCard(response);
            responseContainer.getChildren().add(card);
        }
    }

    private VBox createResponseCard(Response response) {
        VBox card = new VBox();
        card.getStyleClass().add("response-card");
        Label idLabel = new Label("Response ID: " + response.getResponse_id());
        Label requestIdLabel = new Label("Request ID: " + response.getRequest_id());
        Label dateLabel = new Label("Response Date: " + response.getResponse_date());
        Label textLabel = new Label("Response Text: " + response.getResponse_text());
        Label statusLabel = new Label("Response Status: " + response.getResponse_status());
        card.getChildren().addAll(idLabel, requestIdLabel, dateLabel, textLabel, statusLabel);
        card.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowReponseDetails.fxml"));
                Parent root = fxmlLoader.load();
                ShowResponseDetailController controller = fxmlLoader.getController();
                controller.displayResponseDetails(response);
                Scene scene = new Scene(root);
                Stage stage = (Stage) card.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Reclamation Details");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return card;
    }

    @FXML
    private void onRefresh(ActionEvent event) throws SQLException {
        responseContainer.getChildren().clear();
        List<Response> responses = serviceResponse.afficher();
        displayResponses(responses);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowReclamation.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) responseContainer.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Reclamations");
        stage.show();
    }

    public void onAjouter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ajouterResponse.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) responseContainer.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ajouter Response");
        stage.show();
    }
}
