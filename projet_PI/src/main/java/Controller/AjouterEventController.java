package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import entities.event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.eventService;

public class AjouterEventController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text AdresseError;

    @FXML
    private TextField adresseField;

    @FXML
    private Text dateError;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Text errorNom;

    @FXML
    private TextField nomField;
    private event selectedEvent;

    void goBack(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvent.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nomField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void cancel(ActionEvent event) {
        goBack();
    }

    @FXML
    void openEvent(MouseEvent event) {
        goBack();
    }

    @FXML
    void submit(ActionEvent event) {
        String nom = nomField.getText();
        String adresse = adresseField.getText();
        LocalDate localDate = datePicker.getValue();
        boolean isValid = true;
        errorNom.setText("");
        AdresseError.setText("");
        dateError.setText("");
        if (nom.isEmpty()) {
            errorNom.setText("Please fill out Name Field");
            isValid = false;
        }
        if (adresse.isEmpty()) {
            AdresseError.setText("Please fill out Adresse Field");
            isValid = false;
        }
        if (localDate == null) {
            dateError.setText("Please pick up a date");
            isValid = false;
        }

        if(isValid) {

            Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            eventService service = new eventService();

            if(selectedEvent != null){
                boolean isModified = false;

                if(!Objects.equals(selectedEvent.getNom(), nom) || !Objects.equals(selectedEvent.getAdresse(), adresse) || !Objects.equals(selectedEvent.getDate(), new java.sql.Date(date.getTime()))) {
                    isModified = true;
                }

                if(isModified){
                    event e = new event(selectedEvent.getId(), new java.sql.Date(date.getTime()), adresse, nom);
                    service.update(e);
                    goBack();
                } else {
                    errorNom.setText("Nothing has changed");
                    AdresseError.setText("Nothing has changed");
                    dateError.setText("Nothing has changed");
                }
            }
            else {
                event e = new event(new java.sql.Date(date.getTime()),adresse,nom);
                service.addPst(e);
                goBack();
            }

        }
    }

    @FXML
    void initialize() {
        assert AdresseError != null : "fx:id=\"AdresseError\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert adresseField != null : "fx:id=\"adresseField\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert dateError != null : "fx:id=\"dateError\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert errorNom != null : "fx:id=\"errorNom\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert nomField != null : "fx:id=\"nomField\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

    }

    public void setEventToModify(event selectEvent) {
        selectedEvent = selectEvent;

        adresseField.setText(selectEvent.getAdresse());
        Date utilDate = new Date(selectEvent.getDate().getTime());

        Instant instant = utilDate.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        datePicker.setValue(localDate);
        nomField.setText(selectEvent.getNom());
    }

    public void showParticipant(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParticipant.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nomField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
