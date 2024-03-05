package tn.esprit.crud.controllers;

import entities.Reclamation;
import entities.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import tn.esprit.crud.services.ServiceReclamation;
import tn.esprit.crud.services.ServiceResponse;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AjouterResponseController {

    @FXML
    private ComboBox<Reclamation> comboBoxReclamation;

    @FXML
    private TextField tfResponseText;

    private final ServiceResponse service = new ServiceResponse();
    private final ServiceReclamation serviceReclamation = new ServiceReclamation();


    public void initialize() {
        populateComboBox();
    }

    private void populateComboBox() {
        try {
            List<Reclamation> reclamations = serviceReclamation.afficher();
            comboBoxReclamation.getItems().addAll(reclamations);
            // Set display value of ComboBox items to be the name of the Reclamation
            comboBoxReclamation.setConverter(new StringConverter<Reclamation>() {
                @Override
                public String toString(Reclamation reclamation) {
                    return reclamation.getDescription();
                }

                @Override
                public Reclamation fromString(String string) {
                    // Implement if needed
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterBtn(ActionEvent event) throws SQLException {
        List<String> deniedResponses = new ArrayList<>();
        deniedResponses.add("badword");

        if (tfResponseText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        String responseText = tfResponseText.getText();

        for (String deniedResponse : deniedResponses) {
            if (responseText.contains(deniedResponse)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Response denied because of bad word: " + deniedResponse);
                alert.showAndWait();
                return;
            }
        }

        Reclamation selectedReclamation = comboBoxReclamation.getValue();
        LocalDate currentDate = LocalDate.now();
        Date responseDate = Date.valueOf(currentDate);
        String responseStatus = "Envoyer";

        Response response = new Response(selectedReclamation.getRequest_id(), responseDate, responseText, responseStatus);
        service.ajouter(response);
        clearFields();
    }

    private void clearFields() {
        comboBoxReclamation.getSelectionModel().clearSelection();
        tfResponseText.clear();
    }

    public void afficherBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ShowReponse.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) comboBoxReclamation.getScene().getWindow();
        window.setTitle("Liste Responses");
        window.setScene(scene);
        window.show();
    }
}
