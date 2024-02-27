package entities;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Panier {

    /*pour garantir une seule instance de la classe */
    private static Panier INSTANCE;
    public static Panier getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Panier();
        }
        return INSTANCE;
    }


    private Map<String, EntreePanier> entries;  /*La clé pourrait être l'idProduit, la valeur une instance de la classe */
    private Date dateCreation;
    private String etat;
    private float totale;
    public Panier() {
        this.entries = new HashMap<>();
    }


    public Map<String, EntreePanier> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, EntreePanier> entries) {
        this.entries = entries;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }
}
