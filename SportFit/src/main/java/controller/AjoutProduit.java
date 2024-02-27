package controller;

//import com.gluonhq.charm.glisten.control.TextField;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ProduitService;

import java.sql.SQLException;

public class AjoutProduit {

    @FXML
    private TextField categorieFld;

    @FXML
    private TextField imageFld;

    @FXML
    private TextField nomFld;

    @FXML
    private TextField prixFld;

    @FXML
    void AjouterProduit(ActionEvent event) {
        ProduitService produitService = new ProduitService();
        Produit p = new Produit();
        p.setNom(nomFld.getText());
        p.setCategorie(categorieFld.getText());
        p.setPrix(Float.parseFloat(prixFld.getText()));
        p.setImageFile(imageFld.getText());
        try {
            produitService.addPst(p);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Produit Ajouté");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void annuler(ActionEvent event) {

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


        public void setTextField( int id,String nom, String imageFile, float prix, String categorie) {

            int produitId = id;
            nomFld.setText(nom);
            prixFld.setText(String.valueOf(prix));
            imageFld.setText(imageFile);
            categorieFld.setText(categorie);

        }

}