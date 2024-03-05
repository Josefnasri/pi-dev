package tn.esprit.crud.controllers;

import entities.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.crud.services.ServiceResponse;
import tn.esprit.crud.test.HelloApplication;

public class ShowResponseController {
    @FXML
    private FlowPane responseContainer;

    private ServiceResponse serviceResponse = new ServiceResponse();

    public void initialize() throws SQLException {
        List<Response> responses = serviceResponse.afficher();
        displayResponses(responses);
    }

    private void displayResponses(List<Response> responses) {
        for (Response response : responses) {
            VBox card = createResponseCard(response);
            responseContainer.getChildren().add(card);
        }
    }

    private VBox createResponseCard(Response response) {
        VBox card = new VBox();
        card.getStyleClass().add("response-card");
        Label idLabel = new Label("Response ID: " + response.getResponse_id());
        Label requestIdLabel = new Label("Request ID: " + response.getRequest_id());
        Label dateLabel = new Label("Response Date: " + response.getResponse_date());
        Label textLabel = new Label("Response Text: " + response.getResponse_text());
        Label statusLabel = new Label("Response Status: " + response.getResponse_status());
        card.getChildren().addAll(idLabel, requestIdLabel, dateLabel, textLabel, statusLabel);
        card.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ShowReponseDetails.fxml"));
                Parent root = fxmlLoader.load();
                ShowResponseDetailController controller = fxmlLoader.getController();
                controller.displayResponseDetails(response);
                Scene scene = new Scene(root);
                Stage stage = (Stage) card.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Reclamation Details");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return card;
    }

    @FXML
    private void onRefresh(ActionEvent event) throws SQLException {
        responseContainer.getChildren().clear();
        List<Response> responses = serviceResponse.afficher();
        displayResponses(responses);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ShowReclamation.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) responseContainer.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Reclamations");
        stage.show();
    }

    public void onAjouter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ajouterResponse.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) responseContainer.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ajouter Response");
        stage.show();
    }

    public void onExport(ActionEvent actionEvent) {
        try {
            List<Response> responses = serviceResponse.afficher();
            exportToExcel(responses);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void exportToExcel(List<Response> responses) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Response Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Response ID", "Request ID", "Response Date", "Response Text", "Response Status"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Populate data rows
        int rowNum = 1;
        for (Response response : responses) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(response.getResponse_id());
            row.createCell(1).setCellValue(response.getRequest_id());
            row.createCell(2).setCellValue(response.getResponse_date().toString());
            row.createCell(3).setCellValue(response.getResponse_text());
            row.createCell(4).setCellValue(response.getResponse_status());
        }

        // Save the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream("response_data.xlsx")) {
            workbook.write(fileOut);
        }

        // Close the workbook to release resources
        workbook.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Successful");
        alert.setHeaderText(null);
        alert.setContentText("Data has been exported to response_data.xlsx");
        alert.showAndWait();
    }

    public void onStats(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ResponseStatistics.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Response Statistics");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
