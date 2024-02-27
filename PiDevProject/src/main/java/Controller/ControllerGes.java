package Controller;

import Entities.Exercices;
import Entities.Nutritions;
import Service.ExercicesServices;
import Service.NutritionsServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Entities.Exercices;

import java.sql.SQLException;

public class ControllerGes {
private final ExercicesServices ex=new ExercicesServices();
private final NutritionsServices nt=new NutritionsServices();
    @FXML
    private Button Addbtn;

    @FXML
    private TextField ExerciceTitre;

    @FXML
    private TextField ExerciceType;

    @FXML
    private TextField NutritionPlan;
    @FXML
    private Button Updbtn;
    @FXML
    private Button Delbtn;

    @FXML
    void AjoutExerciceNutrition(ActionEvent event)
    {
        ex.add(new Exercices(ExerciceType.getText(), ExerciceTitre.getText()));
        nt.add(new Nutritions(NutritionPlan.getText()));
    }
    @FXML
   void DeleteExerciceNutrition(ActionEvent event) {
           }

    @FXML
    void UpdateExercieNutrition(ActionEvent event) {

    }

}
