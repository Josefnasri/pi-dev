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
import java.util.regex.Pattern;

public class AjouterUser {
    @FXML
    private TextField adresseTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField mdpTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    void afficherUsers(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/AfficherUsers.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouterUser(ActionEvent event) throws SQLException {
        UserService userService = new UserService();
        User user = new User();

        user.setNom(nomTF.getText());
        user.setPrenom(prenomTF.getText());
        user.setAdresse(adresseTF.getText());
        user.setEmail(emailTF.getText());
        user.setMdp(mdpTF.getText());
        /*if(user.getMdp().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("failed");
            alert.setContentText("password failed");
            alert.showAndWait();
            return;
        }
        if(user.getPrenom().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("failed");
            alert.setContentText("prenom failed");
            alert.showAndWait();
            return;
        }
        if(user.getNom().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("failed");
            alert.setContentText("nom failed");
            alert.showAndWait();
            return;
        }

        if(user.getAdresse().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("failed");
            alert.setContentText("adresse failed");
            alert.showAndWait();
            return;
        }
         */

        if(!Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$").matcher(user.getEmail()).matches() ||
        user.getEmail().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("failed");
            alert.setContentText("email failed");
            alert.showAndWait();
            return;
        }
        try {
            userService.ajouter(user);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Personne Ajouter");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }
    @FXML
    void VersModifier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ModifierUser.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VersSupprimer(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/SupprimerUser.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}