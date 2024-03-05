package tn.esprit.crud.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.crud.models.event;
import tn.esprit.crud.services.eventService;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;

public class AfficherEventController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<event, Integer> ratingCo; // Assuming the rating is of type Integer
    @FXML
    private Button supprimerButton;
    @FXML
    private Button moddifierButton;

    @FXML
    private TableColumn<?, ?> adresseCo;

    @FXML
    private TableColumn<?, ?> prixCo;

    @FXML
    private TableColumn<?, ?> dateCo;

    @FXML
    private TableColumn<?, ?> nomCo;

    @FXML
    private TableView<event> table;
    private event selectEvent;

    @FXML
    private TextField searchField;
    @FXML
    void print(ActionEvent event) {
        List<event> list = table.getItems();
        int totalEvents = list.size();

        Map<String, Integer> categoryCountMap = new HashMap<>();
        for (event evt : list) {
            String category = evt.getAdresse();
            categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
        }
        String mostPopularCategory = Collections.max(categoryCountMap.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Create PDF document
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("event_statistics.pdf"));
            document.open();

            document.add(new Paragraph("Event Statistics"));
            document.add(new Paragraph("Total number of events: " + totalEvents));
            document.add(new Paragraph("Most popular location: " + mostPopularCategory));


        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }

        try {
            File pdfFile = new File("event_statistics.pdf");
            Desktop.getDesktop().open(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void search(ActionEvent event) {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<event> filteredList = FXCollections.observableArrayList();
        eventService eventService = new eventService();
        if (!searchText.isEmpty()) {
            event foundEvent = eventService.searchByName(searchText);
            if (foundEvent != null) {
                filteredList.add(foundEvent);
            }
            table.setItems(filteredList);
        } else {
            afficherTable(); // If search field is empty, display all events
        }
    }
    @FXML
    void showStatisituqe(ActionEvent event) {
        Stage stage = new Stage();
        PieChart ratingPieChart = new PieChart();
        List<event> events = eventService.readAllStatic();
        int fiveStarCount = 0;
        int fourStarCount = 0;
        int threeStarCount = 0;
        int twoStarCount = 0;
        int oneStarCount = 0;
        int zeroStarCount = 0;
        for (event evt : events) {
            switch (evt.getRate()){
                case 5:
                    fiveStarCount++;
                    break;
                case 4:
                    fourStarCount++;
                    break;
                case 3:
                    threeStarCount++;
                    break;
                case 2:
                    twoStarCount++;
                    break;
                case 1:
                    oneStarCount++;
                    break;
                case 0:
                    zeroStarCount++;
                    break;
            }
        }

        ObservableList<PieChart.Data> pieChartData = ratingPieChart.getData();
        pieChartData.clear();
        pieChartData.add(new PieChart.Data("5 Stars", fiveStarCount));
        pieChartData.add(new PieChart.Data("4 Stars", fourStarCount));
        pieChartData.add(new PieChart.Data("3 Stars", threeStarCount));
        pieChartData.add(new PieChart.Data("2 Stars", twoStarCount));
        pieChartData.add(new PieChart.Data("1 Star", oneStarCount));
        pieChartData.add(new PieChart.Data("No Stars", zeroStarCount));

        Scene scene = new Scene(ratingPieChart, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Event Statistics");
        stage.show();
    }
    @FXML
    void addEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AjouterEvent.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void moddifierEvent(ActionEvent event) {
        if(selectEvent != null)
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AjouterEvent.fxml"));
                Parent root = loader.load();
                AjouterEventController controller = loader.getController();
                controller.setEventToModify(selectEvent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Ajouter Event");
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void supprimerEvent(ActionEvent event) {
        if (selectEvent != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Are you sure you want to delete this event?");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    eventService eventService = new eventService();
                    eventService.delete(selectEvent);
                    afficherTable();
                }
            });
        }
    }

    @FXML
    void initialize() {
        assert adresseCo != null : "fx:id=\"adresseCo\" was not injected: check your FXML file 'AfficherEvent.fxml'.";
        assert dateCo != null : "fx:id=\"dateCo\" was not injected: check your FXML file 'AfficherEvent.fxml'.";
        assert nomCo != null : "fx:id=\"nomCo\" was not injected: check your FXML file 'AfficherEvent.fxml'.";

        supprimerButton.setDisable(true);
        moddifierButton.setDisable(true);
        afficherTable();
    }

    private void afficherTable() {
        adresseCo.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        dateCo.setCellValueFactory(new PropertyValueFactory<>("date"));
        nomCo.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixCo.setCellValueFactory(new PropertyValueFactory<>("prix"));
        ratingCo.setCellValueFactory(new PropertyValueFactory<>("rate"));
        ratingCo.setCellFactory(column -> {
            return new TableCell<event, Integer>() {
                @Override
                protected void updateItem(Integer rating, boolean empty) {
                    super.updateItem(rating, empty);

                    if (rating == null || empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox starsPane = new HBox();
                        for (int i = 0; i < rating; i++) {
                            Label starLabel = new Label("★");
                            starLabel.setStyle("-fx-text-fill: #dccc1c; -fx-font-family: 'Bauhaus 93'; -fx-font-size: 12;");
                            starsPane.getChildren().add(starLabel);
                        }
                        for (int i = 0; i < 5-rating; i++) {
                            Label starLabel = new Label("☆");
                            starLabel.setStyle("-fx-text-fill: rgb(128,128,128); -fx-font-family: 'Bauhaus 93'; -fx-font-size: 12;");
                            starsPane.getChildren().add(starLabel);
                        }
                        setText(null);
                        setGraphic(starsPane);
                    }
                }
            };
        });

        eventService service = new eventService();
        List<event> listevents = service.readAll();
        ObservableList<event> observableList = FXCollections.observableArrayList(listevents);
        table.setItems(observableList);

        table.setOnMouseClicked(e->{
            selectEvent = table.getSelectionModel().getSelectedItem();

            supprimerButton.setDisable(false);
            moddifierButton.setDisable(false);
        });
    }

    public void showParticipant(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherParticipant.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) supprimerButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Event");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
