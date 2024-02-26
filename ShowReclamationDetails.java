package controllers;

import entities.Reclamation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.ServiceReclamation;
import tn.esprit.reclamation.Main;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editReclamation.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowReclamation.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) statusLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reclamation Details");
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
