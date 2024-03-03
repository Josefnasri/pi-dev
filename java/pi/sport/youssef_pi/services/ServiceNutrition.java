package pi.sport.youssef_pi.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pi.sport.youssef_pi.entite.Nutrition;
import pi.sport.youssef_pi.interfaces.INutrition;
import pi.sport.youssef_pi.utils.Connexion_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceNutrition implements INutrition {
    private Connection connection;
    public ServiceNutrition() {
        try {
            this.connection = Connexion_database.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServiceNutrition.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void ajouterNutrition(Nutrition nutrition) {
        String insertQuery = "INSERT INTO nutrition (meal, details, exercice_id) VALUES (?, ?, ?)";

        try  {
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, nutrition.getMeal());
            preparedStatement.setString(2, nutrition.getDetails());
            preparedStatement.setInt(3, nutrition.getExerciceId()); // Assuming idExercice is the exercise ID

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
    }


    @Override
    public Nutrition recupererNutritionParIdExercice(int idExercice) {
        String selectQuery = "SELECT * FROM nutrition WHERE exercice_id = ?";
        Nutrition nutrition = null;

        try{
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, idExercice);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you have a constructor in Nutrition class to create an instance
                    nutrition = new Nutrition(
                            resultSet.getString("meal"),
                            resultSet.getString("details"),
                            resultSet.getInt("exercice_id")
                    );
                    nutrition.setNutritionId(resultSet.getInt("nutrition_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return nutrition;
    }

    @Override
    public boolean  supprimerNutrition(Nutrition nutrition) {
        if (nutrition != null) {
            int nutritionId = nutrition.getNutritionId();

            String deleteQuery = "DELETE FROM nutrition WHERE nutrition_id = ?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

                preparedStatement.setInt(1, nutritionId);
                preparedStatement.executeUpdate();
                int deletedRows = preparedStatement.executeUpdate();
                return deletedRows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return  false;
                // Handle the exception according to your application's needs
            }
        }
        return false;
    }


    @Override
    public boolean modifierNutrition(Nutrition nutrition) {
            if (nutrition != null) {
                int nutritionId = nutrition.getNutritionId();
                String meal = nutrition.getMeal();
                String details = nutrition.getDetails();

                String updateQuery = "UPDATE nutrition SET meal = ?, details = ? WHERE nutrition_id = ?";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

                    preparedStatement.setString(1, meal);
                    preparedStatement.setString(2, details);
                    preparedStatement.setInt(3, nutritionId);

                    preparedStatement.executeUpdate();
                    int rowsUpdated = preparedStatement.executeUpdate();
                    System.out.println("ok2");

                    // If rowsUpdated is greater than 0, the update was successful
                    return rowsUpdated > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                    // Handle the exception according to your application's needs
                }
            }
return false ;
    }

    @Override
    public ObservableList<Nutrition> afficherTousNutritions() {
        ObservableList<Nutrition> nutritions = FXCollections.observableArrayList();

        String selectQuery = "SELECT n.*, e.nom AS exerciceNom " +
                "FROM nutrition n " +
                "JOIN exercice e ON n.exercice_id = e.exercice_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Assuming you have a constructor in Nutrition class to create an instance
                Nutrition nutrition = new Nutrition(
                        resultSet.getString("meal"),
                        resultSet.getString("details"),
                        resultSet.getInt("exercice_id")
                );
                nutrition.setNutritionId(resultSet.getInt("nutrition_id"));
                // Retrieve the 'exerciceNom' from the result set
                String exerciceNom = resultSet.getString("exerciceNom");
                nutrition.setExercice_nom(exerciceNom);

                nutritions.add(nutrition);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }

        return nutritions;
    }


}
