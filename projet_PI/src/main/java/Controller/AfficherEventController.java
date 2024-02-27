package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.eventService;

public class AfficherEventController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button supprimerButton;
    @FXML
    private Button moddifierButton;

    @FXML
    private TableColumn<?, ?> adresseCo;

    @FXML
    private TableColumn<?, ?> dateCo;

    @FXML
    private TableColumn<?, ?> nomCo;

    @FXML
    private TableView<event> table;
    private event selectEvent;

    @FXML
    void addEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParticipant.fxml"));
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
