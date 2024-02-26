package controllers;

import entities.Reclamation;
import entities.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import services.ServiceReclamation;
import services.ServiceResponse;
import tn.esprit.reclamation.Main;

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

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> choiceBoxStatus;

    private final ServiceResponse service = new ServiceResponse();
    private final ServiceReclamation serviceReclamation = new ServiceReclamation();

    public void initialize() {
        populateComboBox();
        setupChoiceBox();
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
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupChoiceBox() {
        choiceBoxStatus.setValue("Envoyer");
    }

    @FXML
    void ajouterBtn(ActionEvent event) throws SQLException {
        List<String> deniedResponses = new ArrayList<>();
        deniedResponses.add("badword");
        deniedResponses.add("kamel");
        //Ajouter une autre avec deniedResponses.add("badword");

        //control de saissie
        if (tfResponseText.getText().isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        String responseText = tfResponseText.getText();

        //control de badword
        for (String deniedResponse : deniedResponses) {
            if (responseText.contains(deniedResponse)) {
                showAlert("Error", "Response denied because of bad word: " + deniedResponse);
                return;
            }
        }

        Reclamation selectedReclamation = comboBoxReclamation.getValue();
        LocalDate currentDate = datePicker.getValue();
        Date responseDate = Date.valueOf(currentDate);
        String responseStatus = choiceBoxStatus.getValue();

        Response response = new Response(selectedReclamation.getRequest_id(), responseDate, responseText, responseStatus);
        service.ajouter(response);
        clearFields();
    }

    private void clearFields() {
        comboBoxReclamation.getSelectionModel().clearSelection();
        tfResponseText.clear();
        datePicker.setValue(null);
        choiceBoxStatus.setValue("Envoyer");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void afficherBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ShowReponse.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) comboBoxReclamation.getScene().getWindow();
        window.setTitle("Liste Responses");
        window.setScene(scene);
        window.show();
    }
}
