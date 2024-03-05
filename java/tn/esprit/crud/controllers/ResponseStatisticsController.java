package tn.esprit.crud.controllers;

import entities.Response;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import tn.esprit.crud.services.ServiceResponse;

import java.sql.SQLException;
import java.util.List;

public class ResponseStatisticsController {

    @FXML
    private BarChart<String, Number> barChart;

    private final ServiceResponse serviceResponse = new ServiceResponse();

    public void initialize() {
        // Initialize the bar chart with actual data from the service
        try {
            List<Response> responses = serviceResponse.afficher();
            displayStatistics(responses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayStatistics(List<Response> responses) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart.setTitle("Response Statistics");
        xAxis.setLabel("Categories");
        yAxis.setLabel("Values");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Response Data");

        for (Response response : responses) {
            series.getData().add(new XYChart.Data<>(String.valueOf(response.getRequest_id()), response.getResponse_id()));
        }

        barChart.getData().add(series);
    }
}
