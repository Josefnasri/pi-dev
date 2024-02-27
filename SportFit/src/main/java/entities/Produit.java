package entities;

public class Produit {

    public static final Produit BURGER = new Produit("Burger", "burger.jpg", 5.99f);
    public static final Produit SUSHI = new Produit("Sushi", "sushi.jpg", 7.99f);
    public static final Produit TACOS = new Produit("Tacos", "tacos.jpg", 6.49f);

    private int id;
    private String imageFile;
    private float prix;
    private String categorie;
    private String nom;


    public Produit(String name, String imageFile, float price) {
        this.nom = name;
        this.imageFile = imageFile;
        this.prix = price;
    }
    public Produit(){}
    public Produit(String name, String imageFile, float price, String category) {
        this.nom = name;
        this.imageFile = imageFile;
        this.prix = price;
        this.categorie = category;
    }

    public Produit( int id,String name, String imageFile, float price, String category) {
        this.id = id;
        this.nom = name;
        this.imageFile = imageFile;
        this.prix = price;
        this.categorie = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", imageFile='" + imageFile + '\'' +
                ", prix=" + prix +
                ", categorie='" + categorie + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}
