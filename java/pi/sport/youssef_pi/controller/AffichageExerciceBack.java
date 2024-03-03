package pi.sport.youssef_pi.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pi.sport.youssef_pi.HelloApplication;
import pi.sport.youssef_pi.entite.Exercice;
import pi.sport.youssef_pi.services.ServiceExercice;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichageExerciceBack implements Initializable {


    @FXML
    private TableView<Exercice> tableView;
    @FXML
    private TableColumn<Exercice, String> message;
    @FXML
    private TableColumn<Exercice, String> type;
    @FXML
    private TableColumn<String, String> userName;

    @FXML
    private TableColumn<Exercice, String> duree;

    @FXML
    private TableColumn<String, String>  numFois;

    ServiceExercice a = new ServiceExercice();
    public static  Exercice pr ;

    @FXML
    private TextField filtre ;
    ObservableList< Exercice> obList= FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                try {
                    Exercice selectedExercice = tableView.getSelectionModel().getSelectedItem();
                    if (selectedExercice != null) {
                        // = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/pi/sport/youssef_pi/ShowExcercie.fxml"));
                        Parent root = loader.load();
                        ShowExercice controller = loader.getController();
                        controller.setExercice(selectedExercice);
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();

                        //.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void show() {
        ServiceExercice a = new ServiceExercice();
        obList = a.afficherTousExercices();
        type.setCellValueFactory(new PropertyValueFactory<>("nom"));

        userName.setCellValueFactory(new PropertyValueFactory<>("categorie_nom"));
        message.setCellValueFactory(new PropertyValueFactory<>("description"));
       // type.setCellValueFactory(new PropertyValueFactory<>("type"));

        numFois.setCellValueFactory(new PropertyValueFactory<>("nombreDeFois"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));


        tableView.setItems(obList);
    }





    @FXML
    private void Back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pi/sport/youssef_pi/Admin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        };
    }

    @FXML
    private void ajouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pi/sport/youssef_pi/CreateExercice.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        };
    }



    @FXML
    public void handleSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String searchText = filtre.getText().trim();
            if (searchText.isEmpty()) {
                tableView.setItems(obList);
            } else {
                ObservableList<Exercice> filteredList = FXCollections.observableArrayList();
                boolean ExerciceFound = false;
                for (Exercice b : obList) {
                    // search for name or description
                    if ((b.getNom().toLowerCase().contains(searchText.toLowerCase())))
                    {
                        filteredList.add(b);
                        ExerciceFound = true;
                    }
                }
                if (!ExerciceFound) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Exercice non trouvee ");
                    alert.setHeaderText("Aucun Exercice ne correspond � votre recherche");
                    alert.setContentText("Veuillez essayer une autre recherche.");
                    alert.showAndWait();
                }
                tableView.setItems(filteredList);
            }
        }
    }








}


