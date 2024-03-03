package pi.sport.youssef_pi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pi.sport.youssef_pi.HelloApplication;
import pi.sport.youssef_pi.entite.Categorie;
import pi.sport.youssef_pi.entite.Exercice;
import pi.sport.youssef_pi.services.ServiceExercice;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

public class CreateExercice {

    @FXML
    private TextField nameField;
    @FXML
    private TextField  FoisField;
    @FXML
    private TextField dureeField;

    @FXML
    private TextArea description;
    @FXML
    private ComboBox<Categorie> categorieComboBox;

    @FXML
    private ImageView imgview;


    String filePath="";

    private final ServiceExercice exerciceService;


    public CreateExercice() {
        exerciceService = new ServiceExercice();
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
        Categorie selectedExercice = categorieComboBox.getValue();

        if (nameField.getText().isEmpty() ||description.getText().isEmpty() || filePath.isEmpty() || dureeField.getText().isEmpty() || selectedExercice.equals("")||FoisField.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Test");
            alert.setContentText("Check Fields Again");
            alert.showAndWait();
        }
        else {

            String name = nameField.getText();
            String duree = dureeField.getText();

            int numFois = Integer.parseInt(FoisField.getText());
            String disc=description.getText();


            Exercice e = new Exercice(disc,selectedExercice.getCategorieId(), numFois,name,duree, filePath);
            exerciceService.ajouterExercice(e);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Exercice Added Successfully.");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pi/sport/youssef_pi/AffichageExercicceBack.fxml"));
            Parent root = loader.load();

            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Sport");

            stage.show();
            //.close();
        }
    }
    @FXML
    public void initialize() {
        List<Categorie> categoriesList = exerciceService.Read();
        categorieComboBox.setItems(FXCollections.observableArrayList(categoriesList));

// Create a custom StringConverter to handle conversion between Categorie and String
        StringConverter<Categorie> categorieStringConverter = new StringConverter<Categorie>() {
            @Override
            public String toString(Categorie object) {
                return object != null ? object.toString() : null;
            }

            @Override
            public Categorie fromString(String string) {
                // You may choose to implement this method if needed
                return null;
            }
        };

// Set the custom StringConverter to the ComboBox
        categorieComboBox.setConverter(categorieStringConverter);

// Add a listener to the ComboBox to handle selection changes
        categorieComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int selectedCategoryId = newValue.getCategorieId();
                System.out.println("Selected Category ID: " + selectedCategoryId);
            }
        });   }
    @FXML
    void onCancel(ActionEvent event) throws IOException {

        // Load the Modif.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pi/sport/youssef_pi/AffichageExercicceBack.fxml"));
        Parent root = loader.load();

        // Show the Modif.fxml interface
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Sport");

        stage.show();
        //.close();

    }

    @FXML
    private void UploadImageHandle(MouseEvent event) {
        FileChooser fileOpen = new FileChooser();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        File file = fileOpen.showOpenDialog(stage);

        if (file != null) {
            try {
                String path = file.getName(); // file.getPath() 0 security...
                filePath = path;

                // https://img.png
                Image image = new Image(file.toURI().toString(), 100, 100, false, true);
                imgview.setImage(image);

                // Set the destination directory path
                String destDir = "C:\\Users\\user0\\Downloads\\Youssef_Pi\\src\\main\\resources\\pi\\sport\\youssef_pi";

                // Corrected version
                Path sourcePath = Paths.get(file.getAbsolutePath());
                Path destPath = Paths.get(destDir + file.getName());
                Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);


                System.out.println("File uploaded successfully to " + destDir + file.getName());
            } catch (IOException ex) {
                System.out.println("Error uploading file: " + ex.getMessage());
            }
        } else {
            System.out.println("NO PICTURE EXISTS!!");
        }
    }
}
