package tn.esprit.crud.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    public void openModifInterface3(ActionEvent event) {
        try {
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AffichageExercicceBack.fxml"));
            Parent root = loader.load();
            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Sport");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openModifInterface2(ActionEvent event) {
        try {
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AffichageNutritionBack.fxml"));
            Parent root = loader.load();

            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Sport");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void openModifInterface1(ActionEvent event) {
        try {
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/Chart.fxml"));
            Parent root = loader.load();

            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Sport");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

