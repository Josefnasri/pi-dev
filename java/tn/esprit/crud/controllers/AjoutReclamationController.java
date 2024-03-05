package tn.esprit.crud.controllers;

import entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.crud.services.ServiceReclamation;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjoutReclamationController {

    @FXML
    private TextField tfDescription;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    @FXML
    void afficherReclamations(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/reclamation/ShowReclamation.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Liste Reclamations");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void ajouterReclamation(ActionEvent event) {
        try {

            if (tfDescription.getText().isEmpty() || tfDescription.getText().length()>20) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs et de longeur de 20 character");
                alert.showAndWait();
                return;
            }

            int customerId = 0;
            Reclamation reclamation = new Reclamation();
            reclamation.setDescription(tfDescription.getText());
            reclamation.setCustomer_id(customerId);
            LocalDate currentDate = LocalDate.now();
            Date responseDate = Date.valueOf(currentDate);
            reclamation.setRequest_date(responseDate);
            reclamation.setStatus("En Attente");

            serviceReclamation.ajouter(reclamation);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Reclamation envoyer avec succès");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de l'ajout de la réclamation");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
