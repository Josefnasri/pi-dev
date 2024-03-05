package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import tn.esprit.crud.services.IServices;
import tn.esprit.crud.services.UserService;

public class NewPasswordController {

    @FXML
    private PasswordField NewPasswordField;

    @FXML
    private Button send;
    public static String mailUpdate="a";
    @FXML
    void Send(ActionEvent event) throws Exception {
        if(NewPasswordField.getText().isEmpty())
        {   Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Field is empty ");
            alert.showAndWait();
        }
        else
        {
            String newPass = NewPasswordField.getText();
            IServices Iu = new UserService();
            mailUpdate=SendMailController.mail;
            Iu.UpdatePassword(mailUpdate, newPass);
            FXMLLoader loader = new FXMLLoader();
            NewPasswordField.getScene().getWindow().hide();
            Stage prStage = new Stage();
            loader.setLocation(getClass().getResource("/tn/esprit/crud/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            prStage.setResizable(false);
            prStage.show();
        }

    }

    @FXML
    void back(ActionEvent event) {

    }

}
