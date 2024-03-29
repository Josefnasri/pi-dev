package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierUser {

    @FXML
    private TextField idMod;

    @FXML
    private TextField nomNouv;

    @FXML
    private TextField prenomNouv;

    @FXML
    private TextField adresseNouv;

    @FXML
    private TextField emailNouv;

    @FXML
    private TextField mdpNouv;

    private UserService userService;

    {
        try {
            userService = new UserService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifierUser() {
        try {
            // Récupérer les données saisies dans les champs de texte
            int id = Integer.parseInt(idMod.getText());
            String nouveauNom = nomNouv.getText();
            String nouveauPrenom = prenomNouv.getText();
            String nouvelleAdresse = adresseNouv.getText();
            String nouveauEmail = emailNouv.getText();
            String nouveauMdp = mdpNouv.getText();

            // Créer un objet User avec les nouvelles valeurs
            User user = new User(id, nouveauNom, nouveauPrenom, nouvelleAdresse, nouveauEmail, nouveauMdp);

            // Mettre à jour l'utilisateur dans la base de données
            userService.modifier(user);

            // Afficher un message de confirmation
            afficherMessage("Succès", "L'utilisateur a été modifié avec succès.");
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir un ID valide.");
        } catch (SQLException e) {
            afficherErreur("Erreur", "Erreur lors de la modification de l'utilisateur : " + e.getMessage());
        }
    }

    // Méthode pour afficher une boîte de dialogue d'information
    private void afficherMessage(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    // Méthode pour afficher une boîte de dialogue d'erreur
    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void Modification(ActionEvent event) {
        modifierUser();
    }

    @FXML
    void ReturnToAjouter(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/AfficherUsers.fxml"));
        try {
            idMod.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void VersAfficher(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/AfficherUsers.fxml"));
        try {
            idMod.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VersSupprimer(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/SupprimerUser.fxml"));
        try {
            idMod.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
