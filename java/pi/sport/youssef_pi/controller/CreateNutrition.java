package pi.sport.youssef_pi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pi.sport.youssef_pi.HelloApplication;
import pi.sport.youssef_pi.entite.Categorie;
import pi.sport.youssef_pi.entite.Exercice;
import pi.sport.youssef_pi.entite.Nutrition;
import pi.sport.youssef_pi.services.ServiceExercice;
import pi.sport.youssef_pi.services.ServiceNutrition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class CreateNutrition {

    @FXML
    private TextField mealField;


    @FXML
    private TextArea description;
    @FXML
    private ComboBox<Exercice> exerciceComboBox;



    private final ServiceNutrition nutritionService;


    public CreateNutrition() {
        nutritionService = new ServiceNutrition();
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
        Exercice selectedExercice = exerciceComboBox.getValue();

        if (mealField.getText().isEmpty() ||description.getText().isEmpty() ||selectedExercice.equals("") ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Test");
            alert.setContentText("Check Fields Again");
            alert.showAndWait();
        }
        else {

            String meal = mealField.getText();

            String disc=description.getText();


            Nutrition e = new Nutrition(meal,disc,selectedExercice.getExerciceId());
            nutritionService.ajouterNutrition(e);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Exercice Added Successfully.");
            alert.showAndWait();

            Stage stage = (Stage) mealField.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pi/sport/youssef_pi/AffichageNutritionBack.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Sport!");
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void initialize() {
        ServiceExercice sc=new ServiceExercice();
        List<Exercice> categoriesList = sc.afficherTousExercices();
        exerciceComboBox.setItems(FXCollections.observableArrayList(categoriesList));

// Create a custom StringConverter to handle conversion between Categorie and String
        StringConverter<Exercice> categorieStringConverter = new StringConverter<Exercice>() {
            @Override
            public String toString(Exercice object) {
                return object != null ? object.toString() : null;
            }

            @Override
            public Exercice fromString(String string) {
                // You may choose to implement this method if needed
                return null;
            }
        };

// Set the custom StringConverter to the ComboBox
        exerciceComboBox.setConverter(categorieStringConverter);

// Add a listener to the ComboBox to handle selection changes
        exerciceComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int selectedCategoryId = newValue.getCategorieId();
                System.out.println("Selected Category ID: " + selectedCategoryId);
            }
        });   }
    @FXML
    void onCancel(ActionEvent event) throws IOException {

        Stage stage = (Stage) mealField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pi/sport/youssef_pi/AffichageNutritionBack.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sport!");
        stage.setScene(scene);
        stage.show();
        //.close();

    }


}
