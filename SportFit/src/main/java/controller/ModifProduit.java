package controller;

import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ProduitService;

import java.sql.SQLException;

public class ModifProduit {
/*
    @FXML
    private TextField categorieFld;

    @FXML
    private TextField imageFld;

    @FXML
    private TextField nomFld;

    @FXML
    private TextField prixFld;

    private ProduitService produitService;

    {//initialise l'instance produitService en essayant de créer une nouvelle instance de ProduitService
        produitService = new ProduitService();
    }
    @FXML
    void annuler(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void modiferProduit(ActionEvent event) {
        try {
            // Récupérer les données saisies dans les champs de texte
            int id = Integer.parseInt(idMod.getText());
            String nouvelleCateg = categorieFld.getText();
            String nouvelImage = imageFld.getText();
            String nouveauNom = nomFld.getText();
            Float nouveauPrix = Float.valueOf(prixFld.getText());

            // Créer un objet User avec les nouvelles valeurs
            Produit p = new Produit(  id, nouveauNom, nouvelImage, nouveauPrix, nouvelleCateg);

            // Mettre à jour l'utilisateur dans la base de données
            ProduitService.update(p);

            // Afficher un message de confirmation
            afficherMessage("Succès", "L'utilisateur a été modifié avec succès.");
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir un ID valide.");
        } catch (SQLException e) {
            afficherErreur("Erreur", "Erreur lors de la modification de l'utilisateur : " + e.getMessage());
        }
    }
*/
}
