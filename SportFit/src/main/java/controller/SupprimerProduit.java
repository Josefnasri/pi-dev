package controller;

import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ProduitService;
import test.SportFitAdmin;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerProduit {
    @FXML
    private TextField supprimerTF; // TextField pour saisir l'ID de l'utilisateur à supprimer

    private ProduitService produitService;

    {
        produitService = new ProduitService();
    }

    @FXML
    void supprimerUser(ActionEvent event) {
        // Récupérer l'ID de l'utilisateur à partir du TextField
        String idProduitStr = supprimerTF.getText();

        // Vérifier si l'ID est un entier valide
        try {
            int idProduit = Integer.parseInt(idProduitStr);

            // Appeler la méthode de service pour supprimer l'utilisateur par son ID
            Produit p= produitService.readById(idProduit);
            produitService.delete(p);
            System.out.println("Le produit avec l'ID " + idProduitStr + " a été supprimé avec succès.");
            // Vous pouvez ajouter ici des actions supplémentaires après la suppression du produit
        } catch (NumberFormatException e) {
            System.err.println("L'ID du produit doit être un entier valide.");
            // Gérer l'erreur si l'utilisateur entre un ID non valide (par exemple, afficher un message d'erreur à l'utilisateur)
      //  } catch (SQLException e) {
        //    System.err.println("Erreur lors de la suppression du produit : " + e.getMessage());
            // Gérer l'erreur (afficher un message , journaliser l'erreur, etc.)
        }

        // Afficher un message de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setContentText("Le produit a été supprimé avec succès.");
        alert.showAndWait();

    }
    @FXML
    void ReturnToAfficher(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/AfficheProduit.fxml"));
        try {
            supprimerTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void VersAjouter(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/Ajouter1Produit.fxml"));
        try {
            supprimerTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VersModifier(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/ModifierProduit.fxml"));
        try {
            supprimerTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
