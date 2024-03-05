package tn.esprit.crud.controllers;

import javafx.scene.layout.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import entities.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.stage.FileChooser;
import tn.esprit.crud.services.ServiceReclamation;
import tn.esprit.crud.test.HelloApplication;
import javafx.scene.paint.Color;
public class ShowReclamationController {
    public TextField recherField;
    @FXML
    private FlowPane reclamationContainer;
    private ObservableList<Reclamation> allReclamations;
    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    public void initialize() throws SQLException {
        allReclamations = FXCollections.observableArrayList(serviceReclamation.afficher());
        displayReclamations(allReclamations);
    }

    private void displayReclamations(List<Reclamation> reclamations) {
        for (Reclamation reclamation : reclamations) {
            VBox card = createReclamationCard(reclamation);
            reclamationContainer.getChildren().add(card);
        }
    }

    private VBox createReclamationCardFunction(Reclamation reclamation) {
        VBox card = new VBox();
        card.getStyleClass().add("reclamation-card");

        Label requestIdLabel = new Label("Request ID: " + reclamation.getRequest_id());
        Label requestDateLabel = new Label("Request Date: " + reclamation.getRequest_date().toString());
        Label customerIdLabel = new Label("Customer ID: " + reclamation.getCustomer_id());
        Label descriptionLabel = new Label("Description: " + reclamation.getDescription());
        Label statusLabel = new Label("Status: " + reclamation.getStatus());

        card.getChildren().addAll(requestIdLabel, requestDateLabel, customerIdLabel, descriptionLabel, statusLabel);

        card.setOnMouseClicked(event -> openReclamationDetailsPage(reclamation));
        card.setCursor(Cursor.HAND);

        return card;
    }

    private VBox createReclamationCard(Reclamation reclamation) {
        VBox card = new VBox();
        card.getStyleClass().add("reclamation-card");
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));

        Label requestDateLabel = new Label("Request Date: " + reclamation.getRequest_date().toString());
        Label customerIdLabel = new Label("Customer ID: " + reclamation.getCustomer_id());
        Label descriptionLabel = new Label("Description: " + reclamation.getDescription());
        Label statusLabel = new Label("Status: " + reclamation.getStatus());

        card.getChildren().addAll( requestDateLabel, customerIdLabel, descriptionLabel, statusLabel);

        card.setOnMouseClicked(event -> openReclamationDetailsPage(reclamation));
        card.setCursor(Cursor.HAND);
        card.setSpacing(5);
        card.setMaxWidth(400); // Adjust as needed

        return card;
    }


    private void openReclamationDetailsPage(Reclamation reclamation) {
        reclamationContainer.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ShowReclamationDetails.fxml"));
                Parent root = fxmlLoader.load();
                ShowReclamationDetails controller = fxmlLoader.getController();
                controller.displayReclamationDetails(reclamation);
                Scene scene = new Scene(root);
                Stage stage = (Stage) reclamationContainer.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Reclamation Details");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void OnRefresh(ActionEvent actionEvent) throws SQLException {
        List<Reclamation> reclamations = serviceReclamation.afficher();
        reclamationContainer.getChildren().clear();
        displayReclamations(reclamations);
    }

    public void OnAjouter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/AjoutReclamation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Ajouter Reclamation");
        stage.setScene(scene);
        stage.show();
    }

    public void OnReponse(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ShowReponse.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) reclamationContainer.getScene().getWindow();
        stage.setTitle("Liste Reponse");
        stage.setScene(scene);
        stage.show();
    }

    public void OnChatBot(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/Chatbot.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Bot");
        stage.show();
    }

    public void OnRecherche(ActionEvent actionEvent) throws IOException {
        String searchText = recherField.getText().toLowerCase();
        if (!searchText.isEmpty()) {
            for (Reclamation reclamation : allReclamations) {
                if (reclamation.getDescription().toLowerCase().contains(searchText)) {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ShowReclamationDetails.fxml"));
                    Parent root = fxmlLoader.load();
                    ShowReclamationDetails controller = fxmlLoader.getController();
                    controller.displayReclamationDetails(reclamation);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Reclamation Details");
                    stage.show();
                    return;
                }
            }
        }
    }

    public void OnSort(ActionEvent actionEvent) {
        List<Reclamation> sortedReclamations = new ArrayList<>(allReclamations);
        sortedReclamations.sort(Comparator.comparing(Reclamation::getRequest_date));
        reclamationContainer.getChildren().clear();
        displayReclamations(sortedReclamations);
    }

    public void OnExport(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Reclamations List");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            // Show the save file dialog
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                String filePath = file.getAbsolutePath();

                try (PDDocument document = new PDDocument()) {
                    PDPage page = new PDPage();
                    document.addPage(page);

                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        contentStream.beginText(); // Begin text mode
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset(50, 700);

                        contentStream.showText("Reclamations List");

                        int yPosition = 680;
                        for (Reclamation reclamation : allReclamations) {
                            yPosition -= 20;
                            contentStream.newLineAtOffset(0, -20);
                            contentStream.showText("Request ID: " + reclamation.getRequest_id());
                            contentStream.newLineAtOffset(0, -20);
                            contentStream.showText("Request Date: " + reclamation.getRequest_date().toString());
                            contentStream.newLineAtOffset(0, -20);
                            contentStream.showText("Customer ID: " + reclamation.getCustomer_id());
                            contentStream.newLineAtOffset(0, -20);
                            contentStream.showText("Description: " + reclamation.getDescription());
                            contentStream.newLineAtOffset(0, -20);
                            contentStream.showText("Status: " + reclamation.getStatus());
                            contentStream.newLineAtOffset(0, -20);
                        }
                        contentStream.endText();
                    }
                    document.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
