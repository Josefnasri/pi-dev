package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import entities.Produit;
import test.SportFitAdmin;
import services.ProduitService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherProduit {

    @FXML
    private ListView<String> prixCol;

    @FXML
    private ListView<String> categorieCol;

    @FXML
    private ListView<String> idCol;

    @FXML
    private ListView<String> nomCol;

    @FXML
    private ListView<String> imageCol;

    @FXML
    void PageSupprimer(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/SupprimerProduit.fxml"));
        try {
            nomCol.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void PageModifier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/ModifierProduit.fxml"));
        try {
            nomCol.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @FXML
    void ReturnToAjouter(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/AjouterProduit.fxml"));
        try {
            nomCol.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void initialize() throws SQLException {
        ProduitService produitService = null;
        produitService = new ProduitService();
        List<Produit> produits = produitService.readAll();
        ObservableList<String> idList = FXCollections.observableArrayList();
        ObservableList<String> nomList = FXCollections.observableArrayList();
        ObservableList<String> imageList = FXCollections.observableArrayList();
        ObservableList<String> prixList = FXCollections.observableArrayList();
        ObservableList<String> categorieList = FXCollections.observableArrayList();

        for (Produit produit : produits) {
            idList.add(String.valueOf(produit.getId()));
            nomList.add(produit.getNom());
            imageList.add(produit.getImageFile());
            prixList.add(String.valueOf(produit.getPrix()));
            categorieList.add(produit.getCategorie());

        }
        idCol.setItems(idList);
        categorieCol.setItems(categorieList);
        nomCol.setItems(nomList);
        imageCol.setItems(imageList);
        prixCol.setItems(prixList);

    }
}



