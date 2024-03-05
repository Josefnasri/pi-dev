package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.crud.models.event;
import tn.esprit.crud.models.participant;
import tn.esprit.crud.services.eventService;
import tn.esprit.crud.services.participantService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AjouterParticipantController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text addressError;

    @FXML
    private TextField addressField;

    @FXML
    private Text ageError;

    @FXML
    private TextField ageField;

    @FXML
    private ComboBox<String> comboNiveau;

    @FXML
    private ComboBox<String> eventCombo;

    @FXML
    private Text eventError;

    @FXML
    private Text niveauSportifError;

    @FXML
    private Text nomError;

    @FXML
    private TextField nomField;

    @FXML
    private Text prenomError;

    @FXML
    private TextField prenomField;
    private participant selectedParticipant;

    void goBack(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherParticipant.fxml"));
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherEvent.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) niveauSportifError.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void submit(ActionEvent e) {

        boolean isValid = true;
        nomError.setText("");
        prenomError.setText("");
        ageError.setText("");
        addressError.setText("");
        niveauSportifError.setText("");
        eventError.setText("");

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        int age = 0;
        try{
            age = Integer.parseInt(ageField.getText());
        }catch (NumberFormatException err)
        {
            ageError.setText("Please fill out Age Field");
        }
        if(age <18 || age >80){
            ageError.setText("Age should be between 18 and 80");
            isValid = false;
        }
        String address = addressField.getText();
        String niveauSportif = comboNiveau.getValue();
        String eventName = eventCombo.getValue();


        if (nom.isEmpty()) {
            nomError.setText("Please fill out Name Field");
            isValid = false;
        }
        if (address.isEmpty()) {
            addressError.setText("Please fill out Address Field");
            isValid = false;
        }
        if (niveauSportif == null) {
            niveauSportifError.setText("Please select a sport level");
            isValid = false;
        }
        if (eventName == null) {
            eventError.setText("Please select an event");
            isValid = false;
        }
        if (prenom.isEmpty()) {
            prenomError.setText("Please fill out First Name Field");
            isValid = false;
        }

        if (isValid) {
            participantService service = new participantService();

            eventService eventService = new eventService();
            int idEvent = eventService.searchByName(eventName).getId();
            if (selectedParticipant != null) {
                boolean isModified = false;

                if (!Objects.equals(selectedParticipant.getNom(), nom) ||
                        !Objects.equals(selectedParticipant.getPrenom(), prenom) ||
                        !Objects.equals(selectedParticipant.getAddress(), address) ||
                        !Objects.equals(selectedParticipant.getNiveauSportif(), niveauSportif)) {
                    isModified = true;
                }

                if (isModified) {
                    participant updatedParticipant = new participant(selectedParticipant.getId(),nom,prenom,age,address,niveauSportif,idEvent);
                    service.update(updatedParticipant);
                    goBack();
                } else {
                    nomError.setText("Nothing has changed");
                    prenomError.setText("Nothing has changed");
                    ageError.setText("Nothing has changed");
                    addressError.setText("Nothing has changed");
                    niveauSportifError.setText("Nothing has changed");
                    eventError.setText("Nothing has changed");
                }
            } else {
                participant newParticipant = new participant(nom,prenom,age,address,niveauSportif,idEvent);
                service.addPst(newParticipant);
                goBack();
            }
        }
    }
    @FXML
    void initialize() {
        assert addressError != null : "fx:id=\"addressError\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert ageError != null : "fx:id=\"ageError\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert ageField != null : "fx:id=\"ageField\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert comboNiveau != null : "fx:id=\"comboNiveau\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert eventCombo != null : "fx:id=\"eventCombo\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert eventError != null : "fx:id=\"eventError\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert niveauSportifError != null : "fx:id=\"niveauSportifError\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert nomError != null : "fx:id=\"nomError\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert nomField != null : "fx:id=\"nomField\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert prenomError != null : "fx:id=\"prenomError\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert prenomField != null : "fx:id=\"prenomField\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";

        eventService service = new eventService();
        List<event> allEvents = service.readAll();
        for (event event : allEvents) {
            eventCombo.getItems().add(event.getNom());
        }

        ageField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ageField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void setParticipantToModify(participant selectParticipant) {
        selectedParticipant = selectParticipant;

        nomField.setText(selectParticipant.getNom());
        ageField.setText(String.valueOf(selectParticipant.getAge()));
        prenomField.setText(selectParticipant.getPrenom());
        addressField.setText(selectParticipant.getAddress());
        comboNiveau.setValue(selectParticipant.getNiveauSportif());

        eventService service = new eventService();

        eventCombo.setValue(service.searchById(selectParticipant.getId_event()).getNom());
    }
}
