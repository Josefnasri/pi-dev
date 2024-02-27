package Entities;

public class Nutritions
{
    private int Identifiant;
    private String plan;
    private Exercices ID;
    public Nutritions(){};

    public Nutritions( String plan) {
        this.plan = plan;


    }

    public Exercices getID() {
        return ID;
    }

    public void setID(Exercices ID) {
        this.ID = ID;
    }

    public int getIdentifiant() {
        return Identifiant;
    }

    public void setIdentifiant(int ID) {
        this.Identifiant = ID;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Nutrition{" +
                "ID=" + Identifiant +
                ", plan='" + plan + '\'' +
                '}';
    }
}
