package tn.esprit.crud.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.crud.models.Categorie;
import tn.esprit.crud.models.Exercice;
import tn.esprit.crud.utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceExercice implements IExercice {
    private Connection connection;
    public ServiceExercice() {

            this.connection = MyDatabase.getInstance().getConnection();

        }

@Override    public void ajouterExercice(Exercice exercice) {
        String insertQuery = "INSERT INTO exercice (description, categorie_id, nombre_de_fois,nom,duree,image) VALUES (?, ?, ?,?,?,?)";

        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, exercice.getDescription());
            preparedStatement.setInt(2, exercice.getCategorieId()); // Assuming idCategorie is the category ID
            preparedStatement.setInt(3, exercice.getNombreDeFois());
            preparedStatement.setString(4, exercice.getNom());
            preparedStatement.setString(5, exercice.getDuree());
            preparedStatement.setString(6, exercice.getImage());

            preparedStatement.executeUpdate();
            System.out.println(" ajoutÃ©e avec succÃ¨s !");

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }

    @Override
    public Exercice recupererExerciceParIdCategorie(int idCategorie) {
        String selectQuery = "SELECT * FROM exercice WHERE categorie_id = ?";

        try {
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery) ;

            preparedStatement.setInt(1, idCategorie);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have a constructor in Exercice class to create an instance
                    return new Exercice(
                            resultSet.getString("description"),
                            resultSet.getInt("categorie_id"),
                            resultSet.getInt("nombre_de_fois"),
                            resultSet.getString("nom"),
                            resultSet.getString("duree"),
                            resultSet.getString("image")


                            );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

     return null;
    }

    @Override
    public boolean supprimerExercice(Exercice exercice) {
System.out.println("id exer"+exercice.getExerciceId());
        String deleteQuery = "DELETE FROM exercice WHERE exercice_id = ?";

        try {
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

            preparedStatement.setInt(1, exercice.getExerciceId());
            preparedStatement.executeUpdate();
            int deletedRows = preparedStatement.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Hand
            return false;
// le the exception according to your application's needs
        }

    }

    @Override
    public boolean modifierExercice(Exercice exercice) {
        System.out.println("ok1");

        System.out.println("Description: " + exercice.getDescription());
        System.out.println("Nombre de Fois: " + exercice.getNombreDeFois());
        System.out.println("Nom: " + exercice.getNom());
        System.out.println("Duree: " + exercice.getDuree());
        System.out.println("Exercice ID: " + exercice.getExerciceId());

        String updateQuery = "UPDATE exercice SET description = ?, nombre_de_fois= ? ,nom=?,duree=? WHERE exercice_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, exercice.getDescription());
            preparedStatement.setInt(2, exercice.getNombreDeFois());
            preparedStatement.setString(3, exercice.getNom());
            preparedStatement.setString(4, exercice.getDuree());
            preparedStatement.setInt(5, exercice.getExerciceId());

            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("ok2");

            // If rowsUpdated is greater than 0, the update was successful
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
            return false;
        }


    }



    @Override
    public ObservableList<Exercice> afficherTousExercices() {
        ObservableList<Exercice> exercices = FXCollections.observableArrayList();

        String selectQuery = "SELECT e.*, c.nom AS categorie_nom FROM exercice e " +
                "JOIN categorie c ON e.categorie_id = c.categorie_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Exercice exercice = new Exercice(
                        resultSet.getString("description"),
                        resultSet.getInt("categorie_id"),
                        resultSet.getInt("nombre_de_fois"),
                        resultSet.getString("nom"),
                        resultSet.getString("duree"),
                        resultSet.getString("image")
                );
                exercice.setExerciceId(resultSet.getInt("exercice_id"));
                exercice.setCategorie_nom(resultSet.getString("categorie_nom"));

                exercices.add(exercice);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return exercices;
    }

    public List<Categorie> Read() {
        List<Categorie> categories = new ArrayList<>();
        String selectQuery = "SELECT * FROM categorie";

        try  {
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Categorie categorie = new Categorie();
                categorie.setCategorieId(resultSet.getInt("categorie_id"));
                categorie.setNom(resultSet.getString("nom"));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return categories;
    }
}


