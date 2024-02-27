package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class SportFitAdmin extends Application{/*Application est utilisée pour lancer l'application JavaFX.*/

    //Stage est la fenêtre principale de l'application
    //La methode start s'execute lors du lancement de l'application
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SportFitAdmin.class.getResource("/TableProduits.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }



}
