package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.crud.models.participant;
import tn.esprit.crud.services.participantService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherParticipantController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> addressCo;

    @FXML
    private TableColumn<?, ?> ageCo;

    @FXML
    private Button moddifierButton;

    @FXML
    private TableColumn<?, ?> niveauSportifCo;

    @FXML
    private TableColumn<?, ?> nomCo;

    @FXML
    private TableColumn<?, ?> prenomCo;

    @FXML
    private Button supprimerButton;

    @FXML
    private TableView<participant> table;
    private participant selectParticipant;

    @FXML
    void addEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AjouterParticipant.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void moddifierEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AjouterParticipant.fxml"));
            Parent root = loader.load();
            AjouterParticipantController controller = loader.getController();
            controller.setParticipantToModify(selectParticipant);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showEvents(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherEvent.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) moddifierButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimerEvent(ActionEvent event) {
        if (selectParticipant != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Are you sure you want to delete this event?");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    participantService service = new participantService();
                    try {
                        service.delete(selectParticipant);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    afficherTable();
                }
            });
        }
    }

    @FXML
    void initialize() {
        assert addressCo != null : "fx:id=\"addressCo\" was not injected: check your FXML file 'AfficherParticipant.fxml'.";
        assert ageCo != null : "fx:id=\"ageCo\" was not injected: check your FXML file 'AfficherParticipant.fxml'.";
        assert moddifierButton != null : "fx:id=\"moddifierButton\" was not injected: check your FXML file 'AfficherParticipant.fxml'.";
        assert niveauSportifCo != null : "fx:id=\"niveauSportifCo\" was not injected: check your FXML file 'AfficherParticipant.fxml'.";
        assert nomCo != null : "fx:id=\"nomCo\" was not injected: check your FXML file 'AfficherParticipant.fxml'.";
        assert prenomCo != null : "fx:id=\"prenomCo\" was not injected: check your FXML file 'AfficherParticipant.fxml'.";
        assert supprimerButton != null : "fx:id=\"supprimerButton\" was not injected: check your FXML file 'AfficherParticipant.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'AfficherParticipant.fxml'.";

        supprimerButton.setDisable(true);
        moddifierButton.setDisable(true);
        afficherTable();
    }

    private void afficherTable() {
        addressCo.setCellValueFactory(new PropertyValueFactory<>("address"));
        ageCo.setCellValueFactory(new PropertyValueFactory<>("age"));
        niveauSportifCo.setCellValueFactory(new PropertyValueFactory<>("niveauSportif"));
        nomCo.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCo.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        participantService participantService = new participantService();
        List<participant> participants = participantService.readAll();
        table.getItems().setAll(participants);

        table.setOnMouseClicked(e->{
            selectParticipant = table.getSelectionModel().getSelectedItem();

            supprimerButton.setDisable(false);
            moddifierButton.setDisable(false);
        });
    }
}
