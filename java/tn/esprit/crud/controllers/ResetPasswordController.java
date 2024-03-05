package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.crud.services.IServices;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.utils.OTPUtil;

public class ResetPasswordController {

    @FXML
    private TextField cd;

    @FXML
    private Button confirm;

    @FXML
    private Label rec;

    @FXML
    private Label rec1;
    public int code;

    @FXML
    void back(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        rec.getScene().getWindow().hide();
        Stage prStage =new Stage();
        loader.setLocation(getClass().getResource("/tn/esprit/crud/SendMail.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        prStage.setScene(scene);
        prStage.setResizable(false);
        prStage.show();
    }

    @FXML
    void confirmCode(ActionEvent event) throws Exception {
        int codex = Integer.parseInt(cd.getText());
        code = OTPUtil.getOtp();
        IServices Iu = new UserService();
        String x="x";
        if (cd.getText().toString().equals(x))
        {Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Insert verification code");
            alert.showAndWait();
        }

        else if (code == codex) {

            FXMLLoader loader = new FXMLLoader();
            rec.getScene().getWindow().hide();
            Stage prStage = new Stage();
            loader.setLocation(getClass().getResource("/tn/esprit/crud/NewPassword.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            prStage.setScene(scene);
            prStage.setResizable(false);
            prStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect code");
            alert.showAndWait();

        }
    }

}
