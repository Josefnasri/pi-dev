package entities;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
public class event {
    private int id;
    private Date date;
    private String adresse;

    private String nom;



    public event(int id ,Date date,String adresse ,String nom ) {
        this.id = id;
        this.date = date;
        this.adresse = adresse;
        this.nom = nom;
    }
    public event(Date date, String adresse,String nom ) {
        this.adresse = adresse;
        this.date=date;
        this.nom=nom;
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
