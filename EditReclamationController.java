package controllers;

import entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceReclamation;
import tn.esprit.reclamation.Main;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditReclamationController {

    @FXML
    private TextField tfDescription;
    @FXML
    private DatePicker datePicker;

    private Reclamation reclamation;
    private final ServiceReclamation serviceReclamation = new ServiceReclamation();

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        tfDescription.setText(reclamation.getDescription());
    }

    @FXML
    public void updateBtn(ActionEvent actionEvent) {
        try {
            if (tfDescription.getText().isEmpty() || tfDescription.getText().length() > 20) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez remplir tous les champs et de longeur de 20 character");
                alert.showAndWait();
                return;
            }

            reclamation.setRequest_date(Date.valueOf(datePicker.getValue()));
            reclamation.setDescription(tfDescription.getText());
            reclamation.setStatus("En Attente");

            serviceReclamation.modifier(reclamation);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowReclamation.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) tfDescription.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reclamation Details");
            stage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la mise à jour de la réclamation");
            alert.showAndWait();
        }
    }

    @FXML
    public void afficherBtn(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowReclamation.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) tfDescription.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reclamation Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
