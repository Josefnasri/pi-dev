package tn.esprit.crud.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tn.esprit.crud.models.Nutrition;
import tn.esprit.crud.services.ServiceNutrition;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AffichageNutritionBack implements Initializable {


    @FXML
    private TableView<Nutrition> tableView;
    @FXML
    private TableColumn<Nutrition, String> desc;
    @FXML
    private TableColumn<Nutrition, String> meal;
    @FXML
    private TableColumn<String, String> exerciceName;


    ServiceNutrition a = new ServiceNutrition();
    public static  Nutrition pr ;

    @FXML
    private TextField filtre ;
    ObservableList< Nutrition> obList= FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                try {
                    Nutrition selectedNutrition = tableView.getSelectionModel().getSelectedItem();
                    if (selectedNutrition != null) {
                        // = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ShowNutrition.fxml"));
                        Parent root = loader.load();
                        ShowNutrition controller = loader.getController();
                        controller.setNutrition(selectedNutrition);
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
        ServiceNutrition a = new ServiceNutrition();
        obList = a.afficherTousNutritions();
        meal.setCellValueFactory(new PropertyValueFactory<>("meal"));

        exerciceName.setCellValueFactory(new PropertyValueFactory<>("exercice_nom"));
        desc.setCellValueFactory(new PropertyValueFactory<>("details"));

        tableView.setItems(obList);
    }





    @FXML
    private void Back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/crud/Admin.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/crud/CreateNutrition.fxml"));
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
                ObservableList<Nutrition> filteredList = FXCollections.observableArrayList();
                boolean NutritionFound = false;
                for (Nutrition b : obList) {
                    // search for name or description
                    if ((b.getMeal().toLowerCase().contains(searchText.toLowerCase())))
                    {
                        filteredList.add(b);
                        NutritionFound = true;
                    }
                }
                if (!NutritionFound) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Nutrition non trouvee ");
                    alert.setHeaderText("Aucun Nutrition ne correspond � votre recherche");
                    alert.setContentText("Veuillez essayer une autre recherche.");
                    alert.showAndWait();
                }
                tableView.setItems(filteredList);
            }
        }
    }








}


