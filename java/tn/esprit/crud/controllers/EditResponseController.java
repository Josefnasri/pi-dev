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

public class EditResponseController {

    @FXML
    private ComboBox<Reclamation> comboBoxReclamation;

    @FXML
    private TextField tfResponseText;

    private ServiceResponse serviceResponse = new ServiceResponse();
    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    private Response responseToUpdate;

    public void initialize(Response response) {
        this.responseToUpdate = response;
        populateComboBox();

        comboBoxReclamation.setValue(getReclamationById(response.getRequest_id()));
        tfResponseText.setText(response.getResponse_text());
    }

    private void populateComboBox() {
        try {
            List<Reclamation> reclamations = serviceReclamation.afficher();
            comboBoxReclamation.getItems().addAll(reclamations);
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

    private Reclamation getReclamationById(int id) {
        try {
            return serviceReclamation.getReclamationById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    void updateBtn(ActionEvent event) {
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

        if (selectedReclamation == null || tfResponseText.getText().isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        responseToUpdate.setRequest_id(selectedReclamation.getRequest_id());
        responseToUpdate.setResponse_date(responseDate);
        responseToUpdate.setResponse_text(responseText);
        responseToUpdate.setResponse_status(responseStatus);

        try {
            serviceResponse.modifier(responseToUpdate);
            comboBoxReclamation.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void afficherBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ShowReponse.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) comboBoxReclamation.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Response Details");
        stage.show();
    }
}
