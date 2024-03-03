package pi.sport.youssef_pi.entite;

public class Categorie {
    private int categorieId;
    private String nom;

    public Categorie(int categorieId, String nom) {
        this.categorieId = categorieId;
        this.nom = nom;
    }

    public Categorie() {
    }
    @Override
    public String toString() {
        return nom; // Override toString to return the category name
    }
    // Constructeur
    public Categorie(String nom) {
        this.nom = nom;
    }

    // Getter et Setter pour categorieId
    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    // Getter et Setter pour nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Autres méthodes ou logique métier si nécessaire
}

