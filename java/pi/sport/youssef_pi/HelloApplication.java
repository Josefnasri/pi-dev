package pi.sport.youssef_pi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pi.sport.youssef_pi.utils.Connexion_database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        if (testDatabaseConnection()) {
            //admin
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Admin.fxml"));
            //user
    // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AffichageExerciceFront.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sport!");
        stage.setScene(scene);
        stage.show();
        } else {
            // Handle the case where the database connection fails
            System.out.println("Failed to connect to the database. Exiting...");
            // You can add further error handling or show an error message.
        }
    }
    private boolean testDatabaseConnection() {
        try {
            // Attempt to get a connection
            Connection connection = Connexion_database.getInstance().getConnection();
            // If successful, close the connection and return true
            if (connection != null) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Handle any exceptions that occur during the connection attempt
            e.printStackTrace();
        }
        // Return false if the connection fails
        return false;
    }

    public static void main(String[] args) {
        launch();
    }
}