package pi.sport.youssef_pi.entite;

public class Nutrition {
    private int nutritionId;
    private String meal;
    private String details;
    private int exerciceId;
    private String  exercice_nom;

    // Constructeur
    public Nutrition(String meal, String details, int exerciceId) {
        this.meal = meal;
        this.details = details;
        this.exerciceId = exerciceId;
    }

    public Nutrition(String meal, String details, int exerciceId, String exercice_nom) {
        this.meal = meal;
        this.details = details;
        this.exerciceId = exerciceId;
        this.exercice_nom = exercice_nom;
    }

    public Nutrition(int nutritionId, String meal, String details, int exerciceId, String exercice_nom) {
        this.nutritionId = nutritionId;
        this.meal = meal;
        this.details = details;
        this.exerciceId = exerciceId;
        this.exercice_nom = exercice_nom;
    }

    public String getExercice_nom() {
        return exercice_nom;
    }

    public void setExercice_nom(String exercice_nom) {
        this.exercice_nom = exercice_nom;
    }

    // Getter et Setter pour nutritionId
    public int getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(int nutritionId) {
        this.nutritionId = nutritionId;
    }

    // Getter et Setter pour meal
    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    // Getter et Setter pour details
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    // Getter et Setter pour exerciceId
    public int getExerciceId() {
        return exerciceId;
    }

    public void setExerciceId(int exerciceId) {
        this.exerciceId = exerciceId;
    }

    // Autres méthodes ou logique métier si nécessaire
}
