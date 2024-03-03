package pi.sport.youssef_pi.interfaces;

import javafx.collections.ObservableList;
import pi.sport.youssef_pi.entite.Nutrition;

import java.util.List;

public interface INutrition  {
    public void ajouterNutrition(Nutrition nut);
    public Nutrition recupererNutritionParIdExercice(int idExercice);

    // Supprimer une réponse de la base de données
    public boolean supprimerNutrition(Nutrition nut);

    // Modifier une réponse dans la base de données
    public boolean modifierNutrition(Nutrition nut);

    // Récupérer toutes les réponses de la base de données
    public List<Nutrition> afficherTousNutritions();
}
