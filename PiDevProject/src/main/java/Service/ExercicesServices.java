package Service;

import Entities.Exercices;
import utils.DataSource;

import java.sql.*;


public class ExercicesServices implements IExercices<Exercices>
{
    private Connection con;
    private Statement sta;
    private PreparedStatement pst;

    public ExercicesServices() {
        try {
            con = DataSource.getInstance().getCnx();
            if (con == null) {
                throw new NullPointerException("Connection object is null.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to initialize ExercicesCRUD.", ex);
        }
    }

    public void add(Exercices e) {
        String requete = "insert into Exercices ( type, Title) values (?, ?)";
        try {
            pst = con.prepareStatement(requete);
            pst.setString(1, e.getType());
            pst.setString(2, e.getTitle());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String requete = "DELETE FROM Exercices WHERE ID=?";
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }





    @Override
    public void update(int id, String type, String titre){
        String requete = "UPDATE Exercices SET type=?, Title=? WHERE ID=?";
        try {
            pst = con.prepareStatement(requete);
            pst.setString(1, type);
            pst.setString(2, titre);
            pst.setInt(3, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void readall() {
        String requete = "select * from Exercices";
        try {
            sta = con.createStatement();
            ResultSet rs = sta.executeQuery(requete);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String type = rs.getString("type");
                String title = rs.getString("Title");
                // Process the retrieved data as needed
                System.out.println("ID: " + id + ", Type: " + type + ", Title: " + title);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }



}
