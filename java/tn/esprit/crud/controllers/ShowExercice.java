package tn.esprit.crud.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.crud.models.Exercice;
import tn.esprit.crud.services.ServiceExercice;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShowExercice implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tNom.setText("");
        tDuree.setText("");
        tFois.setText("");
        ADesc.setText("");
    }

    @FXML
    private TextField tNom;

    @FXML
    private TextField tDuree;
    @FXML
    private TextField tFois;
    @FXML
    private TextArea ADesc;
    @FXML
    private ComboBox <String>  CType;

private Exercice exercice;
    private Exercice nouveauxexercice;
    private ServiceExercice exerciceService = new ServiceExercice();

    public ShowExercice() {
    }

    public void setExercice(Exercice exercice) {
        this.exercice=exercice;
         tNom.setText(this.exercice.getNom());
        tNom.setEditable(false); // Make the TextField non-editable

        tDuree.setText(this.exercice.getDuree());
        tDuree.setEditable(false);

        tFois.setText(String.valueOf(this.exercice.getNombreDeFois()));
        tFois.setEditable(false);

        ADesc.setText(this.exercice.getDescription());
        ADesc.setEditable(false); // Make the TextArea non-editable


        CType.setValue(exercice.getCategorie_nom());
        CType.setEditable(false); // Make the TextArea non-editable

    }
    @FXML
    void onDelete(MouseEvent event) throws IOException {
        // Afficher un message d'erreur
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Confirmation de suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce exercice ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ServiceExercice sc = new ServiceExercice();
            boolean deleted = sc.supprimerExercice(this.exercice);
        if (exercice != null) {


                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Exercice deleted successfully.");
                successAlert.showAndWait();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pi/sport/youssef_pi/AffichageExercicceBack.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) tNom.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }


             }
        }

    @FXML
    void onEdit(MouseEvent event) {

        this.tNom.setEditable(true);
        this.tDuree.setEditable(true);
        this.tFois.setEditable(true);
        this.ADesc.setEditable(true);


            // Check for empty fields only if the user clicks OK
            if (tNom.getText().isEmpty() || tDuree.getText().isEmpty() || tFois.getText().isEmpty() || ADesc.getText().isEmpty()) {
                Alert validationAlert = new Alert(Alert.AlertType.WARNING);
                validationAlert.setTitle("Validation Error");
                validationAlert.setHeaderText(null);
                validationAlert.setContentText("Le champ ne peut pas être vide.");
                validationAlert.showAndWait();
            }



    }


    @FXML
    void save(MouseEvent event) {
        // Proceed to confirmation
        this.exercice.setDescription( this.ADesc.getText());
        this.exercice.setDuree( this.tDuree.getText());
        this.exercice.setNom(  this.tNom.getText());
        this.exercice.setNombreDeFois(Integer.parseInt( this.tFois.getText()));
        Alert confirmationAlert1 = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert1.setTitle("Confirmation");
        confirmationAlert1.setHeaderText("Confirmation de modification");
        confirmationAlert1.setContentText("Êtes-vous sûr de vouloir modifier ce exercice?");
        System.out.println(this.exercice.toString());
        Optional<ButtonType> result1 = confirmationAlert1.showAndWait();

        if (result1.isPresent() && result1.get() == ButtonType.OK) {
        ServiceExercice sc = new ServiceExercice();
        boolean isModified = sc.modifierExercice(this.exercice);
        if (isModified) {
            // Modification was successful
            showAlert("Success", "Exercice mis à jour avec succès");
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Exercice updated successfully.");
            successAlert.showAndWait();

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pi/sport/youssef_pi/AffichageExercicceBack.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("List Exercice");
                stage.show();
                //.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        } else {
            // Modification failed
            showAlert("WARNING", "Exercice n'a pas été modifié !!!!");
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pi/sport/youssef_pi/AffichageExercicceBack.fxml"));
                    Parent root = loader.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("List Exercice");
                    stage.show();
                    //.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



}