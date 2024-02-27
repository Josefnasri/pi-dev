package controller;

import entities.Panier;
import entities.Produit;
import entities.User;
import services.PanierService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ProduitController /*implements Initializable*/ {
/*
    @FXML
    private GridPane productGridPane;

    private User user;
    /**
     * Initializes the controller class.
     */
/*    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            productGridPane.getChildren().clear();
            Object Product;

            VBox productView1 = productView(Produit.BURGER);
            productGridPane.add(productView1, 0, 0);
            VBox productView2 = productView(Produit.SUSHI);
            productGridPane.add(productView2, 1, 0);
            VBox productView3 = productView(Produit.TACOS);
            productGridPane.add(productView3, 2, 0);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private VBox productView(Produit product) throws FileNotFoundException {
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        FileInputStream input = new FileInputStream("C:\\Users\\fomri\\Documents\\Chahine\\Final\\CRUDPI2\\MyApp\\src\\Assets\\" + product.getImageFile());

        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        Label productName = new Label(product.getNom());
        Label price = new Label(product.getPrix() + "£");

        Button addBtn = new Button("Ajouter dans Panier");
        addBtn.setUserData(product.getNom());
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
          public void handle(ActionEvent actionEvent) {
                //Ajouter le produit to panier
                Node sourceComponent  = (Node)actionEvent.getSource();
                String productName = (String)sourceComponent.getUserData();
                PanierService.getInstance().addProduct(productName,getUser());

            }
        });
        layout.getChildren().addAll(imageView, productName, price, addBtn);
        return layout;

    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
*/
}