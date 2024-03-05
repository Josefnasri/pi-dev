package tn.esprit.crud.controllers;

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
import tn.esprit.crud.models.Exercice;
import tn.esprit.crud.models.Categorie;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.SendingMail;
import tn.esprit.crud.services.ServiceExercice;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;


import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private final UserService us = new UserService();

    public CreateExercice() throws SQLException {
        exerciceService = new ServiceExercice();
    }

    @FXML
    void onSave(ActionEvent event) throws IOException, SQLException {
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
            sendExerciseCreationNotification();
            Stage stage = (Stage) nameField.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/AffichageExercicceBack.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AffichageExercicceBack.fxml"));
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
                String fileName = file.getName();
                String destDir = "src/main/resources/images/";

                Path sourcePath = Paths.get(file.getAbsolutePath());
                Path destPath = Paths.get(destDir + fileName);
                Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);

                filePath = destDir + fileName;

                Image image = new Image(destPath.toUri().toString(), 100, 100, false, true);
                imgview.setImage(image);

                System.out.println("File uploaded successfully to " + destPath);
            } catch (IOException ex) {
                System.out.println("Error uploading file: " + ex.getMessage());
            }
        } else {
            System.out.println("NO PICTURE EXISTS!!");
        }
    }

    public void sendExerciseCreationNotification() throws SQLException {
        List<User> userList = us.recupperer();

        // Regular expression for email format verification
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        for (User user : userList) {
            String recipientEmail = user.getEmail();

            // Verify email format
            Matcher matcher = pattern.matcher(recipientEmail);
            if (!matcher.matches()) {
                // Handle invalid email format
                System.out.println("Invalid email format for user: " + recipientEmail);
                continue; // Skip this user and proceed to the next one
            }

            try {
                SendingMail.sendBody(recipientEmail, "New Exercise Created", "Dear User,A new exercise has been created Best regards,Your Company Name");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
