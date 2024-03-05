package tn.esprit.crud.controllers;

import entities.Reclamation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.crud.services.ServiceReclamation;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class ShowReclamationDetails {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label statusLabel;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    private Reclamation reclamation;

    public void displayReclamationDetails(Reclamation reclamation) {
        this.reclamation = reclamation;
        descriptionLabel.setText("Description: " + reclamation.getDescription());
        statusLabel.setText("Status: " + reclamation.getStatus());
    }

    @FXML
    private void handleEdit() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/editReclamation.fxml"));
        Parent root = fxmlLoader.load();
        EditReclamationController controller = fxmlLoader.getController();
        controller.setReclamation(reclamation);
        Scene scene = new Scene(root);
        Stage stage = (Stage) statusLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Reclamation Details");
        stage.show();
    }

    @FXML
    private void handleDelete() {
        try {
            serviceReclamation.supprimer(reclamation);
            Stage stage = (Stage) descriptionLabel.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error
        }
    }
}
