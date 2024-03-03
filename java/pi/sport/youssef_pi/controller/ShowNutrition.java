package pi.sport.youssef_pi.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pi.sport.youssef_pi.HelloApplication;
import pi.sport.youssef_pi.entite. Nutrition;
import pi.sport.youssef_pi.entite. Nutrition;
import pi.sport.youssef_pi.services.ServiceNutrition;
import pi.sport.youssef_pi.services.ServiceNutrition;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShowNutrition implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        meal.setText("");
        ADesc.setText("");
    }

    @FXML
    private TextField meal;


    @FXML
    private TextArea ADesc;
    @FXML
    private ComboBox <String>  Cex;

    private  Nutrition  nutrition;
    private  Nutrition nouveauxnutrition;
    private ServiceNutrition nutritionService = new ServiceNutrition();

    public ShowNutrition() {
    }

    public void setNutrition( Nutrition  nutrition) {
        this.nutrition= nutrition;
        meal.setText(this.nutrition.getMeal());
        meal.setEditable(false); // Make the TextField non-editable
        ADesc.setText(this.nutrition.getDetails());
        ADesc.setEditable(false); // Make the TextArea non-editable

        Cex.setValue( nutrition.getExercice_nom());
        Cex.setEditable(false); // Make the TextArea non-editable

    }
    @FXML
    void onDelete(MouseEvent event) throws IOException {
        // Afficher un message d'erreur
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Confirmation de suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce  nutrition ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ServiceNutrition sc = new ServiceNutrition();
            boolean deleted = sc.supprimerNutrition(this.nutrition);
            if ( nutrition != null) {


                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText(" Nutrition deleted successfully.");
                successAlert.showAndWait();

                Stage stage = (Stage) meal.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pi/sport/youssef_pi/AffichageNutritionBack.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Sport!");
                stage.setScene(scene);
                stage.show();
            }


        }
    }

    @FXML
    void onEdit(MouseEvent event) {

        this.meal.setEditable(true);

        this.ADesc.setEditable(true);


        // Check for empty fields only if the user clicks OK
        if (meal.getText().isEmpty()    || ADesc.getText().isEmpty()) {
            Alert validationAlert = new Alert(Alert.AlertType.WARNING);
            validationAlert.setTitle("Validation Error");
            validationAlert.setHeaderText(null);
            validationAlert.setContentText("Le champ ne peut pas être vide.");
            validationAlert.showAndWait();
        }



    }


    @FXML
    void save(MouseEvent event) throws IOException {
        // Proceed to confirmation
        this. nutrition.setDetails( this.ADesc.getText());
        this. nutrition.setMeal( this.meal.getText());

        Alert confirmationAlert1 = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert1.setTitle("Confirmation");
        confirmationAlert1.setHeaderText("Confirmation de modification");
        confirmationAlert1.setContentText("Êtes-vous sûr de vouloir modifier ce  nutrition?");
        System.out.println(this. nutrition.toString());
        Optional<ButtonType> result1 = confirmationAlert1.showAndWait();

        if (result1.isPresent() && result1.get() == ButtonType.OK) {
            ServiceNutrition sc = new ServiceNutrition();
            boolean isModified = sc.modifierNutrition(this. nutrition);
            if (isModified) {
                // Modification was successful
                showAlert("Success", " Nutrition mis à jour avec succès");

                Stage stage = (Stage) meal.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pi/sport/youssef_pi/AffichageNutritionBack.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Sport!");
                stage.setScene(scene);
                stage.show();

            } else {
                // Modification failed
                showAlert("WARNING", " Nutrition n'a pas été modifié !!!!");
            }
        }
    }


    private void showAlert(String title, String message) {
        if (title.equals("Success")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
    @FXML
    void retour(MouseEvent event) {

        try {

            Stage stage = (Stage) meal.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pi/sport/youssef_pi/AffichageNutritionBack.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Sport!");
            stage.setScene(scene);
            stage.show();
            //.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}