package Service;

import Entities.Exercices;
import Entities.Nutritions;
import utils.DataSource;

import java.sql.*;

public class NutritionsServices implements INutritions<Nutritions>
{


        private Connection con;
        private Statement sta;
        private PreparedStatement pst;

        public NutritionsServices() {
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

    @Override
    public void add(Nutritions n)
    {
        String requete = "insert into nutrition (plan) values (?)";
        try {
            pst = con.prepareStatement(requete);
            pst.setString(1, n.getPlan());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void update(int identifiant, String plan) {
        String requete = "UPDATE nutrition SET plan=?WHERE Identifiant=?";
        try {
            pst = con.prepareStatement(requete);
            pst.setString(1, plan);
            pst.setInt(2, identifiant);
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int identifiant) {
        String requete = "DELETE FROM Nutrition WHERE Identifiant=?";
        try {
            pst = con.prepareStatement(requete);
            pst.setInt(1, identifiant);
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void readall() {
        String requete = "SELECT * FROM Nutrition";
        try {
            pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Identifiant");
                String plan = rs.getString("plan");
                System.out.println("Identifiant: " + id + ", Plan: " + plan);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
