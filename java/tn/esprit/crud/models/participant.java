package tn.esprit.crud.models;

public class participant {

    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String address;
    private String niveauSportif;
    private int id_event;

    public participant(int id, String nom, String prenom, int age, String address, String niveauSportif, int id_event) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.address = address;
        this.niveauSportif = niveauSportif;
        this.id_event = id_event;
    }

    public participant(String nom, String prenom, int age, String address, String niveauSportif, int id_event) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.address = address;
        this.niveauSportif = niveauSportif;
        this.id_event = id_event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNiveauSportif() {
        return niveauSportif;
    }

    public void setNiveauSportif(String niveauSportif) {
        this.niveauSportif = niveauSportif;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }
}
