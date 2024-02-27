package controller;

//import com.gluonhq.charm.glisten.control.TextField;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.ProduitService;
import test.SportFitAdmin;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierProduit {

    @FXML
    private TextField categNouv;

    @FXML
    private TextField idMod;

    @FXML
    private TextField imageNouv;

    @FXML
    private TextField nomNouv;

    @FXML
    private TextField prixNouv;


    private ProduitService produitService;

    {
        produitService = new ProduitService();
    }

    @FXML
    void modifierUser() {
        try {
            // Récupérer les données saisies dans les champs de texte
            int id = Integer.parseInt(idMod.getText());
            String nNom = nomNouv.getText();
            String nCateg = categNouv.getText();
            String nImage = imageNouv.getText();
            Float nPrix = Float.valueOf(prixNouv.getText());


            // Créer un objet User avec les nouvelles valeurs
            Produit produit = new Produit(  id, nNom, nImage, nPrix, nCateg);;

            // Mettre à jour l'utilisateur dans la base de données
            produitService.update(produit);

            // Afficher un message de confirmation
            afficherMessage("Succès", "L'utilisateur a été modifié avec succès.");
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir un ID valide.");
       // } catch (SQLException e) {
         //   afficherErreur("Erreur", "Erreur lors de la modification de l'utilisateur : " + e.getMessage());
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

    }

    @FXML
    void ReturnToAjouter(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/tn/esprit/crud/AfficherUsers.fxml"));
        try {
            idMod.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void VersAfficher(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/tn/esprit/crud/AfficherUsers.fxml"));
        try {
            idMod.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VersSupprimer(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/tn/esprit/crud/SupprimerUser.fxml"));
        try {
            idMod.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
