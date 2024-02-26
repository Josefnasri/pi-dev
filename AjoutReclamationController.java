package controllers;

import entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceReclamation;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjoutReclamationController {

    @FXML
    private TextField tfDescription;

    @FXML
    private DatePicker datePicker; // Add DatePicker field

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

            //control de saissie
            if (tfDescription.getText().isEmpty() || tfDescription.getText().length() < 10) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs et de longueur de 10 caractères maximum");
                alert.showAndWait();
                return;
            }


            // Validate if a date is selected
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez sélectionner une date.");
                alert.showAndWait();
                return;
            }

            int customerId = 0;
            Reclamation reclamation = new Reclamation();
            reclamation.setDescription(tfDescription.getText());
            reclamation.setCustomer_id(customerId);
            Date requestDate = Date.valueOf(selectedDate); // Convert LocalDate to Date
            reclamation.setRequest_date(requestDate);
            reclamation.setStatus("En Attente");

            serviceReclamation.ajouter(reclamation);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Reclamation envoyée avec succès");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de l'ajout de la réclamation");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
