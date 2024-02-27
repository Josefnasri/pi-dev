package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import test.SportFitAdmin;
import services.ProduitService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficheProduit {

    @FXML
    private TableView<Produit> produitsTable;

    @FXML
    private TableColumn<Produit, String> idCol;

    @FXML
    private TableColumn<Produit, String> nomCol;

    @FXML
    private TableColumn<Produit, String> imageCol;

    @FXML
    private TableColumn<Produit, String> prixCol;

    @FXML
    private TableColumn<Produit, String> categorieCol;

    private ObservableList<Produit> ProduitList = FXCollections.observableArrayList();

    @FXML
    void PageSupprimer(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/SupprimerProduit.fxml"));
        try {
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

   /* @FXML
    void PageModifier(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/Ajouter1Produit.fxml"));
        try {
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
*/
    @FXML
    void Ajouter(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/Ajouter1Produit.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AjoutProduit.class.getName()).log(Level.SEVERE, null, ex);
        }


        /* Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/AjoutProduit.fxml"));
        try {
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }*/
    }

    @FXML
    void initialize() throws SQLException {
        initialiserTableProduits();
        chargerProduits();
    }


    private void initialiserTableProduits() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("imageFile"));
        categorieCol.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));


        // Vous pouvez également définir d'autres propriétés des colonnes ici si nécessaire
        Callback<TableColumn<Produit, String>, TableCell<Produit, String>> cellFoctory = (TableColumn<Produit, String> param) -> {
            // make cell containing buttons
            final TableCell<Produit, String> cell = new TableCell<Produit, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                ProduitService produitService = new ProduitService();
                                Produit produit = produitsTable.getSelectionModel().getSelectedItem();
                                produitService.delete(produit);
                                //refreshTable
                                chargerProduits();
                            } catch (RuntimeException ex) {
                                Logger.getLogger(AfficheProduit.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });


                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            Produit produit = produitsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/Ajouter1Produit.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(AfficheProduit.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AjoutProduit ajoutProduit = loader.getController();
                            if (ajoutProduit != null) {
                                ajoutProduit.setTextField( produit.getId(), produit.getNom(), produit.getImageFile(),produit.getPrix(), produit.getCategorie() );


                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            } else {
                                System.err.println("AjoutProduit controller not found");
                            }


                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };


    //    editCol.setCellFactory(cellFoctory); //a cell factory is responsible for creating and updating cells within a TableView.
        produitsTable.setItems(ProduitList); //refresh
        // Si les données sont grandes, vous pouvez configurer la table pour une meilleure performance
        produitsTable.setPlaceholder(new javafx.scene.control.Label("Aucun produit à afficher"));

        // Ajouter les colonnes à la table
       // produitsTable.getColumns().addAll(idCol, nomCol, imageCol, prixCol, categorieCol);

    }

    private void chargerProduits() {
        ProduitService produitService = new ProduitService();
        List<Produit> produits = produitService.readAll();
        ObservableList<Produit> produitList = FXCollections.observableArrayList(produits);

        produitsTable.setItems(produitList);
    }



/******************************************************************/


}
