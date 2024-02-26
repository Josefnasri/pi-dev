package controllers;

import entities.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.ServiceResponse;
import tn.esprit.reclamation.Main;

import java.io.IOException;
import java.sql.SQLException;

public class ShowResponseDetailController {
    @FXML
    private Label idResponseLabel;

    @FXML
    private Label requestIdLabel;

    @FXML
    private Label dateResponseLabel;

    @FXML
    private Label responseTextLabel;

    @FXML
    private Label responseStatusLabel;

    private Response response;

    private ServiceResponse serviceResponse = new ServiceResponse();

    public void displayResponseDetails(Response response) {
        this.response = response;
        idResponseLabel.setText("Response ID: " + response.getResponse_id());
        requestIdLabel.setText("Request ID: " + response.getRequest_id());
        dateResponseLabel.setText("Response Date: " + response.getResponse_date());
        responseTextLabel.setText("Response Text: " + response.getResponse_text());
        responseStatusLabel.setText("Response Status: " + response.getResponse_status());
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editReponse.fxml"));
            Parent root = fxmlLoader.load();
            EditResponseController controller = fxmlLoader.getController();
            controller.initialize(response);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Response");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        try {
            serviceResponse.supprimer(response);
            // Close the window after deleting the response
            idResponseLabel.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception accordingly
        }
    }
}
