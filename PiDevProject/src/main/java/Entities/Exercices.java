package Entities;

public class Exercices
{
    private int ID;
    private String type;
    private String Title;

    public Exercices( String type, String title) {
        this.type = type;
        Title = title;
    }

    public Exercices() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public String toString() {
        return "Exercices{" +
                "ID=" + ID +
                ", type='" + type + '\'' +
                ", Title='" + Title + '\'' +
                '}';
    }
}