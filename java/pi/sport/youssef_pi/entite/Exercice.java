package pi.sport.youssef_pi.entite;

public class Exercice {
    private int exerciceId;
    private String description;


    private int categorieId;
    private int nombreDeFois;

    // Constructeur
    private String nom;
    private String duree;
    private String image;
    private String  categorie_nom;

    public Exercice(String description, int categorieId, int nombreDeFois, String nom, String duree, String image) {
        this.description = description;
        this.categorieId = categorieId;
        this.nombreDeFois = nombreDeFois;
        this.nom = nom;
        this.duree = duree;
        this.image = image;
    }

    public Exercice() {
    }

    public Exercice(int exerciceId, String description, int categorieId, int nombreDeFois, String nom, String duree, String image) {
        this.exerciceId = exerciceId;
        this.description = description;
        this.categorieId = categorieId;
        this.nombreDeFois = nombreDeFois;
        this.nom = nom;
        this.duree = duree;
        this.image = image;
    }

    public Exercice(String description, int categorieId, int nombreDeFois, String nom, String duree, String image, String categorie_nom) {
        this.description = description;
        this.categorieId = categorieId;
        this.nombreDeFois = nombreDeFois;
        this.nom = nom;
        this.duree = duree;
        this.image = image;
        this.categorie_nom = categorie_nom;
    }

    public Exercice(String description, int nombreDeFois, String nom, String duree) {
        this.description = description;
        this.nombreDeFois = nombreDeFois;
        this.nom = nom;
        this.duree = duree;
    }

    @Override
    public String toString() {
        return nom;
    }

    public String getCategorie_nom() {
        return categorie_nom;
    }

    public void setCategorie_nom(String categorie_nom) {
        this.categorie_nom = categorie_nom;
    }

    public String getNom() {
        return nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    // Getter et Setter pour exerciceId
    public int getExerciceId() {
        return exerciceId;
    }

    public void setExerciceId(int exerciceId) {
        this.exerciceId = exerciceId;
    }

    // Getter et Setter pour description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter et Setter pour categorieId
    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    // Getter et Setter pour nombreDeFois
    public int getNombreDeFois() {
        return nombreDeFois;
    }

    public void setNombreDeFois(int nombreDeFois) {
        this.nombreDeFois = nombreDeFois;
    }

    // Autres méthodes ou logique métier si nécessaire
}
