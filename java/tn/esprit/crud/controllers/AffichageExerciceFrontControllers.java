package tn.esprit.crud.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tn.esprit.crud.models.Exercice;
import tn.esprit.crud.models.Nutrition;
import tn.esprit.crud.services.ServiceExercice;
import tn.esprit.crud.services.ServiceNutrition;
import tn.esprit.crud.test.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AffichageExerciceFrontControllers implements Initializable {

    @FXML
    private ListView<Exercice> exerciceListView;
    private FilteredList<Exercice> filteredExercices;

    @FXML
    private TextField filtre;
    @FXML
    private TextField weightField;

    @FXML
    private TextField heightField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exerciceListView.setCellFactory(new ExerciceCellFactory());

        ServiceExercice a = new ServiceExercice();

        List<Exercice> exercices = a.afficherTousExercices();

        // Set the items in the ListView
        exerciceListView.getItems().addAll(exercices);
        // Créer la FilteredList
        filteredExercices = new FilteredList<>(FXCollections.observableList(exercices));

// Initialiser votre ListView
        exerciceListView.setItems(filteredExercices);
    }


    public class ExerciceCellFactory implements Callback<ListView<Exercice>, ListCell<Exercice>> {

        @Override
        public ListCell<Exercice> call(ListView<Exercice> param) {
            return new ListCell<Exercice>() {

                @Override
                protected void updateItem(Exercice item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        // Create a custom cell with image, text, and a button
                        HBox cellContent = createCellContent(item);
                        setGraphic(cellContent);
                    }
                }
            };
        }

        private HBox createCellContent(Exercice exercice) {
            System.out.println("/pi/sport/youssef_pi/" + exercice.getImage());


            ImageView imageView = null;
            try {
                File file = new File("D:\\youssef\\Youssef_Pi\\src\\main\\resources\\pi\\sport\\youssef_pi\\" + exercice.getImage());
                String imagePath = file.toURI().toURL().toString();
                imageView = new ImageView(new Image(imagePath));
                imageView.setFitWidth(130);
                imageView.setFitHeight(130);

                // Rest of your code...
            } catch (Exception e) {
                e.printStackTrace();
            }

            Label titleLabel = new Label("Exercice Details:");
            titleLabel.setStyle("-fx-font-size: 16; -fx-text-fill: blue;"); // Adjust color and font size
            Label typeLabel = new Label("Nom:" + exercice.getNom());
            typeLabel.setStyle("-fx-font-size: 14; ;"); // Adjust color and font size

            Label dateLabel = new Label("Type:" + exercice.getCategorie_nom());
            dateLabel.setStyle("-fx-font-size: 14; ;"); // Adjust color and font size
            Label statutLabel = new Label("Duree: " + exercice.getDuree());


            Button reponseButton = new Button(" Nutrition ");
            reponseButton.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: skyblue;");

            reponseButton.setOnAction(event -> {
                ServiceNutrition sp = new ServiceNutrition();

                Nutrition r = sp.recupererNutritionParIdExercice(exercice.getExerciceId());

                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Nutrition:");
                dialog.setHeaderText("Votre Nutrition ");

                // Ajouter les champs de texte à la boîte de dialogue
                TextArea reponseArea = new TextArea(r.getDetails());
                reponseArea.setPromptText("Description");
                TextField dateField = new TextField(r.getMeal());
                dateField.setPromptText("Meal");

                GridPane grid = new GridPane();
                grid.add(new Label("Description:"), 0, 0);
                grid.add(reponseArea, 1, 0); // Utiliser reponseArea à la place de reponseField
                grid.add(new Label("Meal:"), 0, 1);
                grid.add(dateField, 1, 1);

                dialog.getDialogPane().setContent(grid);

                // Ajouter les boutons nécessaires (par exemple, OK et Annuler)
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

                // Afficher la boîte de dialogue et attendre la fermeture
                Optional<Pair<String, String>> result = dialog.showAndWait();

            });


            VBox detailsBox = new VBox(5);
            detailsBox.getChildren().addAll(titleLabel, typeLabel, dateLabel, statutLabel);

            HBox cellContent = new HBox(10);
            cellContent.getChildren().addAll(imageView, detailsBox, reponseButton);

            return cellContent;
        }

    }

    @FXML
    public void handleSearch(KeyEvent event) {
        // Récupérer le texte de la recherche
        String searchTerm = filtre.getText().toLowerCase();

        // Créer un prédicat pour filtrer par statut
        Predicate<Exercice> filterByStatut = exercice -> {
            String statut = exercice.getCategorie_nom().toLowerCase();
            return statut.contains(searchTerm);
        };

        // Appliquer le prédicat au FilteredList
        filteredExercices.setPredicate(filterByStatut);
    }


    @FXML
    public void Back(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) filtre.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/pi/sport/youssef_pi//User.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        ;

    }

    @FXML
    public void Calcul(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Poids Ideal Calculator");
        dialog.setHeaderText("Enter Weight and Height:");


        // Ajouter les champs de texte à la boîte de dialogue
        TextField poids = new TextField();
        poids.setPromptText("Poids");

        TextField typeField = new TextField();
        typeField.setPromptText("Taille");

        GridPane updateGrid = new GridPane();
        updateGrid.add(new Label("Poids en KG:"), 0, 0);
        updateGrid.add(poids, 1, 0);
        updateGrid.add(new Label("Taille en cm:"), 0, 1);
        updateGrid.add(typeField, 1, 1);

        dialog.getDialogPane().setContent(updateGrid);

        // Ajouter les boutons nécessaires (par exemple, Modifier et Annuler)
        ButtonType updateButtonType = new ButtonType("Calcul", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        // Activer le bouton Modifier uniquement si les champs sont valides
        Node updateButtonNode = dialog.getDialogPane().lookupButton(updateButtonType);
        updateButtonNode.setDisable(true);

        // Validation des champs avant d'activer le bouton Modifier
        poids.textProperty().addListener((observable, oldValue, newValue) -> {
            updateButtonNode.setDisable(newValue.trim().isEmpty() || typeField.getText().trim().isEmpty());
        });

        typeField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateButtonNode.setDisable(newValue.trim().isEmpty() || poids.getText().trim().isEmpty());
        });

        // Définir la logique pour le bouton Modifier
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return new Pair<>(poids.getText(), typeField.getText());
            }
            return null;
        });

        // Afficher la boîte de dialogue de mise à jour et attendre la fermeture
        Optional<Pair<String, String>> updateResult = dialog.showAndWait();

        // Traiter le résultat de la mise à jour si présent
        updateResult.ifPresent(messageTypePair -> {
            System.out.println("kg" + poids.getText() + "taiille en cm" + typeField.getText());
            double height = Double.parseDouble(typeField.getText());
            double weight = Double.parseDouble(poids.getText());

            try {
                double calories = makeApiRequest(weight, height);
                showAlert("Ideal Poids Calculation Result", "Poids Ideal: " + calories+" KG");
            } catch (IOException e) {
                showAlert("API Error", "Error connecting to the API. Please try again later.");
                e.printStackTrace();
            }
            /// double weight = Double.parseDouble(weightField.getText());
            // double height = Double.parseDouble(heightField.getText());
            // double calories = calculateCalories(weight, height);
            // Mettre à jour la base de données ou effectuer d'autres actions
            // Utilisez les valeurs de updatedMessage et updatedType
            // par exemple, serviceReclamation.update(reclamation.getId_reclamation(), updatedMessage, updatedType);
        });


    }

    private double makeApiRequest(double weight, double height) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String apiUrl = "https://fitness-calculator.p.rapidapi.com/idealweight?gender=male&height=" + height;

        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .addHeader("X-RapidAPI-Key", "b07be1ba47msha5d2dac94b52b30p18f524jsn9b3e83b7db4c")
                .addHeader("X-RapidAPI-Host", "fitness-calculator.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            System.out.println("API Response: " + responseBody); // Print the entire JSON response

            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Extract the "Hamwi" value from the "data" field
            double hamwiCalories = jsonNode.path("data").path("Hamwi").asDouble();

            return hamwiCalories;
        } else {
            throw new IOException("API request failed");
        }
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Add OK button to the alert
        ButtonType okButton = new ButtonType("OK", ButtonType.OK.getButtonData());
        alert.getButtonTypes().setAll(okButton);

        // Show the alert and wait for user response
        alert.showAndWait();

    }

}



