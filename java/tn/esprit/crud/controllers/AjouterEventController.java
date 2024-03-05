package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.crud.models.event;
import tn.esprit.crud.services.eventService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class AjouterEventController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label star1;

    @FXML
    private Label star2;

    @FXML
    private Label star3;

    @FXML
    private Label star4;

    @FXML
    private Text errorLocalisation;

    @FXML
    private TextField localisationField;

    @FXML
    private Label star5;

    private int rating = 0;
    @FXML
    private Text imageName;

    @FXML
    private Text imageUploadError;
    @FXML
    private Text AdresseError;

    @FXML
    private Text prixError;

    @FXML
    private TextField prixField;

    @FXML
    private TextField adresseField;

    @FXML
    private Text dateError;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Text errorNom;

    @FXML
    private TextField nomField;
    private event selectedEvent;

    void goBack(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherEvent.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nomField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void cancel(ActionEvent event) {
        goBack();
    }

    @FXML
    void openEvent(MouseEvent event) {
        goBack();
    }

    @FXML
    void submit(ActionEvent event) {
        String nom = nomField.getText();
        String adresse = adresseField.getText();
        LocalDate localDate = datePicker.getValue();
        String image = imageName.getText();
        String localisation = localisationField.getText();
        boolean isValid = true;
        errorNom.setText("");
        AdresseError.setText("");
        dateError.setText("");
        prixError.setText("");
        imageUploadError.setText("");
        errorLocalisation.setText("");
        if (nom.isEmpty()) {
            errorNom.setText("Please fill out Name Field");
            isValid = false;
        }
        if (image.isEmpty()) {
            imageUploadError.setText("Please upload an Image");
            isValid = false;
        }
        if (adresse.isEmpty()) {
            AdresseError.setText("Please fill out Adresse Field");
            isValid = false;
        }
        if (localDate == null) {
            dateError.setText("Please pick up a date");
            isValid = false;
        }

        if (localisation.isEmpty()) {
            errorLocalisation.setText("Please fill out Localisation Field");
            isValid = false;
        } else {
            // Regular expression to match the required format
            String regex = "^-?\\d{1,2}\\.\\d{1,15},\\s?-?\\d{1,3}\\.\\d{1,15}$";
            if (!localisation.matches(regex)) {
                errorLocalisation.setText("Invalid localisation format");
                isValid = false;
            }
        }

        float prix = 0;
        String prixText = prixField.getText();
        if (prixText.isEmpty()) {
            prixError.setText("Please fill out Price Field");
            isValid = false;
        } else {
            try {
                prix = Float.parseFloat(prixText);
                // Additional validation for price if needed
                if (prix <= 0) {
                    prixError.setText("Price must be greater than zero");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                prixError.setText("Invalid price format");
                isValid = false;
            }
        }


        if(isValid) {

            Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            eventService service = new eventService();

            if(selectedEvent != null){
                boolean isModified = false;

                if(!Objects.equals(selectedEvent.getNom(), nom) ||!Objects.equals(selectedEvent.getPrix(), prix)||!Objects.equals(selectedEvent.getLocalisation(), localisation)||!Objects.equals(selectedEvent.getRate(), rating)||!Objects.equals(selectedEvent.getImage(), image) || !Objects.equals(selectedEvent.getAdresse(), adresse) || !Objects.equals(selectedEvent.getDate(), new java.sql.Date(date.getTime()))) {
                    isModified = true;
                }

                if(isModified){
                    event e = new event(selectedEvent.getId(), new java.sql.Date(date.getTime()), adresse, nom,prix,image,rating,localisation);
                    service.update(e);
                    goBack();
                } else {
                    errorNom.setText("Nothing has changed");
                    AdresseError.setText("Nothing has changed");
                    dateError.setText("Nothing has changed");
                    prixError.setText("Nothing has changed");
                    errorLocalisation.setText("Nothing has changed");
                }
            }
            else {
                event e = new event(new java.sql.Date(date.getTime()),adresse,nom,prix,image,rating,localisation);
                service.addPst(e);
                goBack();
            }

        }
    }

    @FXML
    void initialize() {
        assert AdresseError != null : "fx:id=\"AdresseError\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert adresseField != null : "fx:id=\"adresseField\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert dateError != null : "fx:id=\"dateError\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert errorNom != null : "fx:id=\"errorNom\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert nomField != null : "fx:id=\"nomField\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

    }

    public void setEventToModify(event selectEvent) {
        selectedEvent = selectEvent;

        adresseField.setText(selectEvent.getAdresse());
        Date utilDate = new Date(selectEvent.getDate().getTime());

        Instant instant = utilDate.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        datePicker.setValue(localDate);
        nomField.setText(selectEvent.getNom());
        prixField.setText(String.valueOf(selectEvent.getPrix()));
        imageName.setText(selectEvent.getImage());
        localisationField.setText(selectEvent.getLocalisation());
        updateStarLabels(selectEvent.getRate());
        rating = selectEvent.getRate();
    }

    public void showParticipant(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherParticipant.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nomField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            imageName.setText(file.getName());
        }
        String fileName = imageName.getText();
        if (fileName != null && !fileName.isEmpty()) {
            try {
                // Get the resource URL for the uploads directory
                URL resourceUrl = getClass().getClassLoader().getResource("upload");
                if (resourceUrl == null) {
                    // If the directory doesn't exist, create it
                    File uploadsDirectory = new File("src/main/resources/upload/events");
                    if (!uploadsDirectory.exists()) {
                        uploadsDirectory.mkdirs();
                    }
                    resourceUrl = uploadsDirectory.toURI().toURL();
                }

                // Copy the uploaded file to the uploads directory
                Files.copy(new File(file.getPath()).toPath(), Paths.get(resourceUrl.toURI()).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace(); // Handle exception properly based on your application's requirements
            }
        }
    }

    @FXML
    void handleStarClick(MouseEvent event) {
        Label clickedStar = (Label) event.getSource();
        String starId = clickedStar.getId();

        int clickedStarIndex = Integer.parseInt(starId.substring(4)) - 1; // Get star index

        rating = clickedStarIndex + 1;

        // Update star labels
        updateStarLabels(rating);
    }
    private void updateStarLabels(int rate) {
        setAllStarsToGray(); // Set all stars to gray initially

        for (int i = 0; i < rate; i++) {
            switch (i) {
                case 0:
                    star1.setText("★");
                    star1.setTextFill(Paint.valueOf("#dccc1c"));
                    break;
                case 1:
                    star2.setText("★");
                    star2.setTextFill(Paint.valueOf("#dccc1c"));
                    break;
                case 2:
                    star3.setText("★");
                    star3.setTextFill(Paint.valueOf("#dccc1c"));
                    break;
                case 3:
                    star4.setText("★");
                    star4.setTextFill(Paint.valueOf("#dccc1c"));
                    break;
                case 4:
                    star5.setText("★");
                    star5.setTextFill(Paint.valueOf("#dccc1c"));
                    break;
                default:
                    break;
            }
        }
    }

    private void setAllStarsToGray() {
        star1.setText("☆");
        star1.setTextFill(Color.GRAY);
        star2.setText("☆");
        star2.setTextFill(Color.GRAY);
        star3.setText("☆");
        star3.setTextFill(Color.GRAY);
        star4.setText("☆");
        star4.setTextFill(Color.GRAY);
        star5.setText("☆");
        star5.setTextFill(Color.GRAY);
    }

}
