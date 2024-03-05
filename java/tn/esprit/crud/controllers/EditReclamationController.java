package tn.esprit.crud.controllers;
import entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.crud.services.ServiceReclamation;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditReclamationController {

    @FXML
    private TextField tfDescription;

    private Reclamation reclamation;
    private final ServiceReclamation serviceReclamation = new ServiceReclamation();

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        tfDescription.setText(reclamation.getDescription());
    }

    @FXML
    public void updateBtn(ActionEvent actionEvent) throws SQLException, IOException {
        if (tfDescription.getText().isEmpty() || tfDescription.getText().length()>20) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs et de longeur de 20 character");
            alert.showAndWait();
            return;
        }
        LocalDate currentDate = LocalDate.now();
        Date responseDate = Date.valueOf(currentDate);
        reclamation.setRequest_date(responseDate);
        reclamation.setDescription(tfDescription.getText());
        reclamation.setStatus("En Attente");
        serviceReclamation.modifier(reclamation);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ShowReclamation.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) tfDescription.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Reclamation Details");
        stage.show();
    }

    @FXML
    public void afficherBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ShowReclamation.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) tfDescription.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Reclamation Details");
        stage.show();
    }
}
