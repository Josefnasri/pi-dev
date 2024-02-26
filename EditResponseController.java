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

public class EditResponseController {

    @FXML
    private ComboBox<Reclamation> comboBoxReclamation;

    @FXML
    private TextField tfResponseText;

    @FXML
    private DatePicker datePicker; // New DatePicker

    @FXML
    private ChoiceBox<String> choiceBoxStatus; // New ChoiceBox

    private ServiceResponse serviceResponse = new ServiceResponse();
    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    private Response responseToUpdate;

    public void initialize(Response response) {
        this.responseToUpdate = response;
        populateComboBox();

        comboBoxReclamation.setValue(getReclamationById(response.getRequest_id()));
        tfResponseText.setText(response.getResponse_text());

        // Set the DatePicker value to the response's date
        datePicker.setValue(response.getResponse_date().toLocalDate());

        // Set the ChoiceBox value to the response's status
        choiceBoxStatus.setValue(response.getResponse_status());
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
        LocalDate selectedDate = datePicker.getValue();
        String selectedStatus = choiceBoxStatus.getValue();

        if (selectedReclamation == null || tfResponseText.getText().isEmpty() || selectedDate == null || selectedStatus == null) {
            System.out.println("Please fill in all fields.");
            return;
        }

        Date responseDate = Date.valueOf(selectedDate);

        responseToUpdate.setRequest_id(selectedReclamation.getRequest_id());
        responseToUpdate.setResponse_date(responseDate);
        responseToUpdate.setResponse_text(responseText);
        responseToUpdate.setResponse_status(selectedStatus);

        try {
            serviceResponse.modifier(responseToUpdate);
            comboBoxReclamation.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void afficherBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowReponse.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) comboBoxReclamation.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Response Details");
        stage.show();
    }
}
