package tn.esprit.crud.models;

import java.util.Date;
public class event {
    private int id;
    private Date date;
    private String adresse;

    private String nom;

    private float prix;
    private String image;
    private int rate;
    private String localisation;

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public event(int id , Date date, String adresse , String nom , float prix, String image,int rate,String localisation) {
        this.id = id;
        this.date = date;
        this.adresse = adresse;
        this.nom = nom;
        this.prix = prix;
        this.image = image;
        this.rate = rate;
        this.localisation = localisation;
    }
    public event(Date date, String adresse,String nom ,float prix, String image,int rate,String localisation) {
        this.adresse = adresse;
        this.date=date;
        this.nom=nom;
        this.prix = prix;
        this.image = image;
        this.rate = rate;
        this.localisation = localisation;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "event{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", adresse='" + adresse + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}
